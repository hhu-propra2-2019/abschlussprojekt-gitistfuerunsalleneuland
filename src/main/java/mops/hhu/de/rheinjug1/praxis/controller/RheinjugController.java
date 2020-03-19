package mops.hhu.de.rheinjug1.praxis.controller;

import static mops.hhu.de.rheinjug1.praxis.models.Account.createAccountFromPrincipal;
import static mops.hhu.de.rheinjug1.praxis.thymeleaf.ThymeleafAttributesHelper.ACCOUNT_ATTRIBUTE;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import mops.hhu.de.rheinjug1.praxis.models.Account;
import mops.hhu.de.rheinjug1.praxis.models.SubmissionEventInfo;
import mops.hhu.de.rheinjug1.praxis.models.SubmissionEventInfoDateComparator;
import mops.hhu.de.rheinjug1.praxis.services.MeetupService;
import mops.hhu.de.rheinjug1.praxis.services.SubmissionEventInfoService;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
public class RheinjugController {

  private final Counter authenticatedAccess;

  private final Counter publicAccess;

  private final MeetupService meetupService;
  private final SubmissionEventInfoService submissionEventInfoService;

  @Autowired
  public RheinjugController(
      final MeterRegistry registry,
      final MeetupService meetupService,
      final SubmissionEventInfoService submissionEventInfoService) {
    authenticatedAccess = registry.counter("access.authenticated");
    publicAccess = registry.counter("access.public");
    this.meetupService = meetupService;
    this.submissionEventInfoService = submissionEventInfoService;
  }

  @GetMapping("/")
  @Secured({"ROLE_orga", "ROLE_studentin"})
  public String home(final KeycloakAuthenticationToken token, final Model model) {
    if (token != null) {
      final Account account = createAccountFromPrincipal(token);
      model.addAttribute(ACCOUNT_ATTRIBUTE, account);
    }
    model.addAttribute("events", meetupService.getUpcomingEvents());
    publicAccess.increment();
    return "home";
  }

  @GetMapping("/events")
  @Secured({"ROLE_orga", "ROLE_studentin"})
  public String showAllEventsSortedByDate(final KeycloakAuthenticationToken token, final Model model) {

    final Account account = createAccountFromPrincipal(token);

    final List<SubmissionEventInfo> submissionEventInfos =
        submissionEventInfoService.getAllEventsWithInfosByEmail(account);
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
    return "admin/statistics";
  }
}
