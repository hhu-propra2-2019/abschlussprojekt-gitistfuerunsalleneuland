package mops.hhu.de.rheinjug1.praxis.adapters.web.controller;

import static mops.hhu.de.rheinjug1.praxis.adapters.web.thymeleaf.ThymeleafAttributesHelper.ACCOUNT_ATTRIBUTE;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import mops.hhu.de.rheinjug1.praxis.domain.Account;
import mops.hhu.de.rheinjug1.praxis.domain.AccountFactory;
import mops.hhu.de.rheinjug1.praxis.domain.chart.ChartService;
import mops.hhu.de.rheinjug1.praxis.domain.event.Event;
import mops.hhu.de.rheinjug1.praxis.domain.event.MeetupService;
import mops.hhu.de.rheinjug1.praxis.domain.submission.eventinfo.SubmissionEventInfo;
import mops.hhu.de.rheinjug1.praxis.domain.submission.eventinfo.SubmissionEventInfoComparator;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
public class RheinjugController {

  private final Counter authenticatedAccess;
  private final MeetupService meetupService;
  private final Counter publicAccess;
  private final ChartService chartService;
  private final AccountFactory accountFactory;

  @Autowired
  public RheinjugController(
      final MeterRegistry registry,
      final MeetupService meetupService,
      final ChartService chartService,
      final AccountFactory accountFactory) {
    authenticatedAccess = registry.counter("access.authenticated");
    publicAccess = registry.counter("access.public");
    this.meetupService = meetupService;
    this.chartService = chartService;
    this.accountFactory = accountFactory;
  }

  @GetMapping("/")
  @Secured({"ROLE_orga", "ROLE_studentin"})
  public String home(final KeycloakAuthenticationToken token, final Model model) {
    if (token != null) {
      final Account account = accountFactory.createFromToken(token);
      model.addAttribute(ACCOUNT_ATTRIBUTE, account);
    }
    final List<Event> upcoming = meetupService.getEventsByStatus("upcoming");
    model.addAttribute("events", upcoming);
    publicAccess.increment();
    return "home";
  }

  @GetMapping("/events")
  @Secured({"ROLE_orga", "ROLE_studentin"})
  public String showAllEvents(
      final KeycloakAuthenticationToken token,
      final SubmissionEventInfoComparator comparator,
      final Model model) {

    final Account account = accountFactory.createFromToken(token);

    final List<SubmissionEventInfo> submissionEventInfos =
        meetupService.getAllEventsWithInfosByEmail(account);
    submissionEventInfos.sort(comparator);

    model.addAttribute(ACCOUNT_ATTRIBUTE, account);
    model.addAttribute("eventsWithInfos", submissionEventInfos);
    publicAccess.increment();
    return "allEvents";
  }

  @GetMapping("/logout")
  public String logout(final HttpServletRequest request) throws ServletException {
    request.logout();
    return "redirect:/";
  }

  @GetMapping("/admin/statistics")
  @Secured("ROLE_orga")
  public String getStatistics(final KeycloakAuthenticationToken token, final Model model) {
    if (token != null) {
      model.addAttribute(ACCOUNT_ATTRIBUTE, accountFactory.createFromToken(token));
    }
    model.addAttribute("chart", chartService.getXEventsChart(6));
    model.addAttribute(
        "numberEntwickelbarReceipts",
        String.valueOf(chartService.getNumberOfReceiptsByMeetupType(MeetupType.ENTWICKELBAR)));
    model.addAttribute(
        "numberRheinjugReceipts",
        String.valueOf(chartService.getNumberOfReceiptsByMeetupType(MeetupType.RHEINJUG)));
    return "admin/statistics";
  }

  @GetMapping({"/update/{page}", "/update"})
  public String update(@PathVariable(required = false) final String page) {
    meetupService.update();
    return page == null ? "redirect:/" : "redirect:/" + page;
  }
}
