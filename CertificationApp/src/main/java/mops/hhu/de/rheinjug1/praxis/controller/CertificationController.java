package mops.hhu.de.rheinjug1.praxis.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import mops.hhu.de.rheinjug1.praxis.domain.Receipt;
import mops.hhu.de.rheinjug1.praxis.domain.ReceiptForm;
import mops.hhu.de.rheinjug1.praxis.services.ReceiptService;
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
  private final ReceiptService receiptService = new ReceiptService();
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
    model.addAttribute("receipts", new ReceiptForm());
    publicAccess.increment();
    return "uebersicht";
  }

  @PostMapping("/")
  @Secured({"ROLE_student", "ROLE_orga"})
  public String submitReceipt(
      final KeycloakAuthenticationToken token, final Model model, final ReceiptForm receiptFiles) {
    if (token != null) {
      model.addAttribute("account", createAccountFromPrincipal(token));
    }
    model.addAttribute("receipts", receiptFiles);
    Receipt receipt = null;
    try {
      receipt = receiptService.read(receiptFiles.getNewReceipt());
    } catch (IOException e) {
      catchIOException();
    }
    if (receiptService.verify(receipt)) {
      receiptFiles.addReceipt();
    } else {
      stuffToDoIfReceiptIsInvalid();
    }
    return "uebersicht";
  }

  private void stuffToDoIfReceiptIsInvalid() {
    // TODO Auto-generated method stub

  }

  private void catchIOException() {
    // TODO Auto-generated method stub

  }

  @GetMapping("/logout")
  public String logout(final HttpServletRequest request) throws ServletException {
    request.logout();
    return "redirect:/";
  }
}
