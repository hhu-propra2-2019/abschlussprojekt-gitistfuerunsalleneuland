package mops.hhu.de.rheinjug1.praxis.controller;

import static mops.hhu.de.rheinjug1.praxis.models.Account.createAccountFromPrincipal;
import static mops.hhu.de.rheinjug1.praxis.thymeleaf.ThymeleafAttributesHelper.ACCOUNT_ATTRIBUTE;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.models.Account;
import mops.hhu.de.rheinjug1.praxis.models.Summary;
import mops.hhu.de.rheinjug1.praxis.services.ChartService;
import mops.hhu.de.rheinjug1.praxis.services.MeetupService;
import mops.hhu.de.rheinjug1.praxis.services.ReceiptService;
import mops.hhu.de.rheinjug1.praxis.models.Account;
import mops.hhu.de.rheinjug1.praxis.models.SubmissionEventInfo;
import mops.hhu.de.rheinjug1.praxis.models.SubmissionEventInfoDateComparator;
import mops.hhu.de.rheinjug1.praxis.services.MeetupService;
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
  @Autowired private ChartService chartService;
  @Autowired private ReceiptService receiptService;


  @Autowired
  public RheinjugController(final MeterRegistry registry, final MeetupService meetupService) {
    authenticatedAccess = registry.counter("access.authenticated");
    publicAccess = registry.counter("access.public");
    this.meetupService = meetupService;
  }

  @GetMapping("/")
  @Secured({"ROLE_orga", "ROLE_studentin"})
  public String home(final KeycloakAuthenticationToken token, final Model model) {
    if (token != null) {
      final Account account = createAccountFromPrincipal(token);
      model.addAttribute(ACCOUNT_ATTRIBUTE, account);
    }
    final List<Event> upcoming = meetupService.getEventsByStatus("upcoming");
    model.addAttribute("events", upcoming);
    publicAccess.increment();
    return "home";
  }

  @GetMapping("/events")
  @Secured({"ROLE_orga", "ROLE_studentin"})
  public String showAllEvents(final KeycloakAuthenticationToken token, final Model model) {

    final Account account = createAccountFromPrincipal(token);

    final List<SubmissionEventInfo> submissionEventInfos =
        meetupService.getAllEventsWithInfosByEmail(account);
    submissionEventInfos.sort(new SubmissionEventInfoDateComparator());

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
      model.addAttribute(ACCOUNT_ATTRIBUTE, createAccountFromPrincipal(token));
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
