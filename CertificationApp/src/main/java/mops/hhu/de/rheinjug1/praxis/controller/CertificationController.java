package mops.hhu.de.rheinjug1.praxis.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import mops.hhu.de.rheinjug1.praxis.domain.InputHandler;
import mops.hhu.de.rheinjug1.praxis.services.CertificationService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rheinjug1")
@SuppressWarnings({
  "PMD.UnusedPrivateField",
  "PMD.SingularField",
  "PMD.UnusedImports",
  "PMD.AvoidDuplicateLiterals"
})
public class CertificationController {

  private final Counter authenticatedAccess;
  private final CertificationService certificationService = new CertificationService();
  private final Counter publicAccess;

  public CertificationController(final MeterRegistry registry) {
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
  public String uebersicht(final KeycloakAuthenticationToken token, final Model model) {
    if (token != null) {
      model.addAttribute("account", createAccountFromPrincipal(token));
    }
    model.addAttribute("input", new InputHandler());
    // model.addAttribute("receiptList", new VerifiedReceiptList());
    publicAccess.increment();
    return "uebersicht";
  }

  @PostMapping("/")
  @Secured({"ROLE_student", "ROLE_orga"})
  public String submitReceipt(
      final KeycloakAuthenticationToken token, final Model model, final InputHandler input) {
    if (token != null) {
      model.addAttribute("account", createAccountFromPrincipal(token));
    }

    model.addAttribute("input", input);
    if (input.areRheinjugUploadsOkForCertification()) {
      certificationService.createCertification(input);
      // und sowas wie mailservice.send
    }
    if (input.isEntwickelbarUploadOkForCertification()) {
      certificationService.createCertification(input);
      // und sowas wie mailservice.send
    }
    return "uebersicht";
  }

  @GetMapping("/logout")
  public String logout(final HttpServletRequest request) throws ServletException {
    request.logout();
    return "redirect:/";
  }
}
