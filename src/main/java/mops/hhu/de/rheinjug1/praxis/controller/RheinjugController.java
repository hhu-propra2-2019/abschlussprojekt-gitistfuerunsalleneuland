package mops.hhu.de.rheinjug1.praxis.controller;

import static mops.hhu.de.rheinjug1.praxis.models.Account.createAccountFromPrincipal;
import static mops.hhu.de.rheinjug1.praxis.thymeleaf.ThymeleafAttributesHelper.ACCOUNT_ATTRIBUTE;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import mops.hhu.de.rheinjug1.praxis.models.Summary;
import mops.hhu.de.rheinjug1.praxis.services.MeetupService;
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

  @Autowired
  public RheinjugController(final MeterRegistry registry, final MeetupService meetupService) {
    authenticatedAccess = registry.counter("access.authenticated");
    publicAccess = registry.counter("access.public");
    this.meetupService = meetupService;
  }

  @GetMapping("/")
  public String home(final KeycloakAuthenticationToken token, final Model model) {
    if (token != null) {
      model.addAttribute(ACCOUNT_ATTRIBUTE, createAccountFromPrincipal(token));
    }
    model.addAttribute("events", meetupService.getUpcomingEvents());
    publicAccess.increment();
    return "home";
  }

  @GetMapping("/talk")
  @Secured("ROLE_orga")
  public String statistics(final KeycloakAuthenticationToken token, final Model model) {
    if (token != null) {
      model.addAttribute(ACCOUNT_ATTRIBUTE, createAccountFromPrincipal(token));
    }
    model.addAttribute("summaryForm", new Summary());
    return "talk";
  }

  @GetMapping("/logout")
  public String logout(final HttpServletRequest request) throws ServletException {
    request.logout();
    return "redirect:/";
  }

  @GetMapping("/profil")
  public String profil(final KeycloakAuthenticationToken token, final Model model) {
    if (token != null) {
      model.addAttribute(ACCOUNT_ATTRIBUTE, createAccountFromPrincipal(token));
    }
    return "profil";
  }

  @GetMapping("/statistics")
  public String talk(final KeycloakAuthenticationToken token, final Model model) {
    if (token != null) {
      model.addAttribute(ACCOUNT_ATTRIBUTE, createAccountFromPrincipal(token));
    }
    return "statistics";
  }
}
