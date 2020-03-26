package mops.hhu.de.rheinjug1.praxis.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import mops.hhu.de.rheinjug1.praxis.domain.Account;
import mops.hhu.de.rheinjug1.praxis.domain.CertificationData;
import mops.hhu.de.rheinjug1.praxis.domain.InputHandler;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.interfaces.ReceiptReaderInterface;
import mops.hhu.de.rheinjug1.praxis.interfaces.ReceiptVerificationInterface;
import mops.hhu.de.rheinjug1.praxis.services.CertificationService;
import mops.hhu.de.rheinjug1.praxis.services.ChartService;
import mops.hhu.de.rheinjug1.praxis.services.VerificationService;
import mops.hhu.de.rheinjug1.praxis.services.YamlReceiptReader;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

import static mops.hhu.de.rheinjug1.praxis.thymeleaf.ThymeleafAttributesHelper.*;

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
  private final ReceiptReaderInterface fileReaderService = new YamlReceiptReader();
  private final ReceiptVerificationInterface verificationService = new VerificationService();
  @Autowired
  private ChartService chartService;

  public CertificationController(final MeterRegistry registry) {
    authenticatedAccess = registry.counter("access.authenticated");
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
  @Secured({"ROLE_studentin", "ROLE_orga"})
  public String home(final KeycloakAuthenticationToken token, final Model model) {
    if (token != null) {
      final Account account = createAccountFromPrincipal(token);
      model.addAttribute(ACCOUNT_ATTRIBUTE, account);
    }
    model.addAttribute(CERTIFICATION_ATTRIBUTE, new CertificationData());
    model.addAttribute(INPUT_ATTRIBUTE, new InputHandler(/*fileReaderService, verificationService*/));
    authenticatedAccess.increment();
    return "home";
  }

  @PostMapping("/")
  @Secured({"ROLE_student", "ROLE_orga"})
  public String submitReceipt(
      final KeycloakAuthenticationToken token, final Model model, final InputHandler inputHandler, final CertificationData certificationData)
      throws InvalidKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException,
          UnrecoverableEntryException, SignatureException, IOException {

	    final Account account = createAccountFromPrincipal(token);
	    certificationData.setName(account.getName()); // vor- und nachname evtl. trennen
	    System.out.println(account.getEmail());
	  
    if (inputHandler.areRheinjugUploadsOkForCertification() && inputHandler.verifyRheinjug()) {
      certificationService.createCertification(inputHandler);
      
    }
    if (inputHandler.isEntwickelbarUploadOkForCertification()
        && inputHandler.verifyEntwickelbar()) {
      certificationService.createCertification(inputHandler);
    }

    
    model.addAttribute(ACCOUNT_ATTRIBUTE, account);
    model.addAttribute(INPUT_ATTRIBUTE, inputHandler);
    authenticatedAccess.increment();
    return "home";
  }

  @GetMapping("admin/statistics/")
  @Secured("ROLE_orga")
  public String showStatistics(final KeycloakAuthenticationToken token, final Model model) {
    if (token != null) {
      model.addAttribute(ACCOUNT_ATTRIBUTE, createAccountFromPrincipal(token));
    }
    model.addAttribute(
            "numberEntwickelbarReceipts",
            String.valueOf(chartService.getNumberOfReceiptsByMeetupType(MeetupType.ENTWICKELBAR)));
    model.addAttribute(
            "numberRheinjugReceipts",
            String.valueOf(chartService.getNumberOfReceiptsByMeetupType(MeetupType.RHEINJUG)));
    return "admin/statistics";
  }

  @GetMapping("/logout")
  public String logout(final HttpServletRequest request) throws ServletException {
    request.logout();
    return "redirect:/";
  }
}
