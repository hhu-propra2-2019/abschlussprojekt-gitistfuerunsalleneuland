package mops.hhu.de.rheinjug1.praxis.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import static mops.hhu.de.rheinjug1.praxis.thymeleaf.ThymeleafAttributesHelper.ACCOUNT_ATTRIBUTE;
import static mops.hhu.de.rheinjug1.praxis.thymeleaf.ThymeleafAttributesHelper.FORM_USER_DATA_ATTRIBUTE;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;
import mops.hhu.de.rheinjug1.praxis.domain.Account;
import mops.hhu.de.rheinjug1.praxis.domain.FormUserData;
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
import mops.hhu.de.rheinjug1.praxis.domain.RheinjugCertificationData;
import mops.hhu.de.rheinjug1.praxis.services.CertificationService;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
  @Autowired
  private ChartService chartService;
  private final CertificationService certificationService;


  @Autowired
  public CertificationController(
      final MeterRegistry registry, final CertificationService certificationService) {
    authenticatedAccess = registry.counter("access.authenticated");
    this.certificationService = certificationService;
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
    authenticatedAccess.increment();

    model.addAttribute(FORM_USER_DATA_ATTRIBUTE, new FormUserData());
    return "home";
  }

  @PostMapping(value = "/", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  @Secured({"ROLE_studentin", "ROLE_orga"})
  public @ResponseBody byte[] submitReceipt(
      final KeycloakAuthenticationToken token, final Model model, final FormUserData formUserData)
      throws InvalidKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException,
          UnrecoverableEntryException, SignatureException, IOException, JAXBException,
          Docx4JException {

    final Account account = createAccountFromPrincipal(token);
    authenticatedAccess.increment();
    model.addAttribute(ACCOUNT_ATTRIBUTE, account);

    final InputHandler inputHandler = new InputHandler();
    inputHandler.setRheinjugReceipts(formUserData);
    final RheinjugCertificationData rheinjugCertificationData =
        new RheinjugCertificationData(formUserData);

    if (inputHandler.areRheinjugUploadsOkForCertification() && inputHandler.verifyRheinjug()) {
      rheinjugCertificationData.setEventTitles(inputHandler.getEventTitles());
      return certificationService.createCertification(rheinjugCertificationData);
    }
    if (inputHandler.isEntwickelbarUploadOkForCertification()
        && inputHandler.verifyEntwickelbar()) {
      certificationService.createCertification(rheinjugCertificationData);
    }

    return new byte[0];
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
    return "redirect:/rheinjug1/";
  }
}
