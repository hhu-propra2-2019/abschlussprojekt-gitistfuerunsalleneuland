package mops.hhu.de.rheinjug1.praxis.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RheinjugController {

  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField", "PMD.UnusedImports"})
  private final Counter authenticatedAccess;

  private final Counter publicAccess;

  public RheinjugController(final MeterRegistry registry) {
    authenticatedAccess = registry.counter("access.authenticated");
    publicAccess = registry.counter("access.public");
  }

  private Account createAccountFromPrincipal(final KeycloakAuthenticationToken token) {
    final KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
    return new Account(
        principal.getName(),
        principal.getKeycloakSecurityContext().getIdToken().getEmail(),
        null,
        token.getAccount().getRoles());
  }

  @GetMapping("/")
  public String rheinjug(final KeycloakAuthenticationToken token, final Model model) {
    if (token != null) {
      model.addAttribute("account", createAccountFromPrincipal(token));
    }
    publicAccess.increment();
    return "rheinjug";
  }

  @GetMapping("/logout")
  public String logout(final HttpServletRequest request) throws ServletException {
    request.logout();
    return "redirect:/";
  }

  @GetMapping("/statistics")
  @Secured("ROLE_orga")
  public String statistics(final KeycloakAuthenticationToken token, final Model model) {
    model.addAttribute("account", createAccountFromPrincipal(token));
    return "statistics";
  }

  @GetMapping("/talk")
  public String talk() {
    return "talk";
  }
}