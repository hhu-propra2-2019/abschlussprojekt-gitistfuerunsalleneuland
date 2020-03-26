package mops.hhu.de.rheinjug1.praxis.adapters.web;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;
import mops.hhu.de.rheinjug1.praxis.adapters.web.thymeleaf.ThymeleafAttributesHelper;
import mops.hhu.de.rheinjug1.praxis.domain.Account;
import mops.hhu.de.rheinjug1.praxis.domain.AccountFactory;
import mops.hhu.de.rheinjug1.praxis.domain.certification.CertificationData;
import mops.hhu.de.rheinjug1.praxis.domain.certification.CertificationService;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@SessionAttributes("inputHandler")
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
  private final CertificationService certificationService;
  private final AccountFactory accountFactory;
  private final InputHandler inputHandler;

  @Autowired
  public CertificationController(
      final MeterRegistry registry,
      final CertificationService certificationService,
      final AccountFactory accountFactory,
      final InputHandler inputHandler) {
    authenticatedAccess = registry.counter("access.authenticated");
    this.certificationService = certificationService;
    this.accountFactory = accountFactory;
    this.inputHandler = inputHandler;
  }

  @GetMapping("/")
  @Secured({"ROLE_studentin", "ROLE_orga"})
  public String home(final KeycloakAuthenticationToken token, final Model model) {
    if (token != null) {
      final Account account = accountFactory.createFromPrincipal(token);
      model.addAttribute(ThymeleafAttributesHelper.ACCOUNT_ATTRIBUTE, account);
    }
    authenticatedAccess.increment();

    model.addAttribute(ThymeleafAttributesHelper.INPUT_ATTRIBUTE, inputHandler);
    model.addAttribute(ThymeleafAttributesHelper.FORM_USER_DATA_ATTRIBUTE, new FormUserData());
    return "home";
  }

  @PostMapping(
      value = "/",
      produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
  @Secured({"ROLE_studentin", "ROLE_orga"})
  public @ResponseBody byte[] submitReceipt(
      final KeycloakAuthenticationToken token,
      final Model model,
      final FormUserData formUserData,
      final InputHandler inputHandler)
      throws InvalidKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException,
          UnrecoverableEntryException, SignatureException, IOException, JAXBException,
          Docx4JException { // sollten im inputHandler gefangen werden
    final Account account = accountFactory.createFromPrincipal(token);
    authenticatedAccess.increment();
    model.addAttribute(ThymeleafAttributesHelper.ACCOUNT_ATTRIBUTE, account);

    final CertificationData rheinjugCertificationData = formUserData.toRheinjugCertificationData();
    if (inputHandler.areRheinjugUploadsOkForCertification() && inputHandler.verifyRheinjug()) {
      rheinjugCertificationData.setEventTitles(inputHandler.getEventTitles());
      return certificationService.createCertification(rheinjugCertificationData);
    }
    if (inputHandler.isEntwickelbarUploadOkForCertification()
        && inputHandler.verifyEntwickelbar()) {
      certificationService.createCertification(rheinjugCertificationData);
      // missing
    }

    inputHandler.reset();
    home(token, model);
    return new byte[0];
  }

  @GetMapping("/logout")
  public String logout(final HttpServletRequest request) throws ServletException {
    request.logout();
    return "redirect:/rheinjug1/";
  }
}
