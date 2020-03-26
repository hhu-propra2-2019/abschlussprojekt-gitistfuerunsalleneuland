package mops.hhu.de.rheinjug1.praxis.controller;

import static mops.hhu.de.rheinjug1.praxis.auth.RolesHelper.STUDENTIN;
import static mops.hhu.de.rheinjug1.praxis.models.Account.createAccountFromPrincipal;
import static mops.hhu.de.rheinjug1.praxis.thymeleaf.ThymeleafAttributesHelper.ACCOUNT_ATTRIBUTE;
import static mops.hhu.de.rheinjug1.praxis.thymeleaf.ThymeleafAttributesHelper.ALL_SUBMISSIONS_FROM_USER_ATTRIBUTE;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import javax.mail.MessagingException;
import mops.hhu.de.rheinjug1.praxis.database.entities.Submission;
import mops.hhu.de.rheinjug1.praxis.exceptions.EventNotFoundException;
import mops.hhu.de.rheinjug1.praxis.exceptions.SubmissionNotFoundException;
import mops.hhu.de.rheinjug1.praxis.exceptions.UnauthorizedSubmissionAccessException;
import mops.hhu.de.rheinjug1.praxis.models.Account;
import mops.hhu.de.rheinjug1.praxis.models.Receipt;
import mops.hhu.de.rheinjug1.praxis.services.SubmissionEventInfoService;
import mops.hhu.de.rheinjug1.praxis.services.receipt.ReceiptCreationAndStorageService;
import mops.hhu.de.rheinjug1.praxis.services.receipt.ReceiptSendService;
import mops.hhu.de.rheinjug1.praxis.services.submission.SubmissionAccessService;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

@Controller
@RequestMapping("/user/submissions")
public class SubmissionUserController {

  @Value("${submission.template.path}")
  private String submissionTemplatePath;

  private final SubmissionAccessService submissionAccessService;
  private final ReceiptCreationAndStorageService receiptCreationAndStorageService;
  private final ReceiptSendService receiptSendService;
  private final SubmissionEventInfoService submissionEventInfoService;

  @Autowired
  public SubmissionUserController(
      final SubmissionAccessService submissionAccessService,
      final ReceiptCreationAndStorageService receiptCreationAndStorageService,
      final ReceiptSendService receiptSendService,
      final SubmissionEventInfoService submissionEventInfoService) {
    this.submissionAccessService = submissionAccessService;
    this.receiptCreationAndStorageService = receiptCreationAndStorageService;
    this.receiptSendService = receiptSendService;
    this.submissionEventInfoService = submissionEventInfoService;
  }

  @GetMapping
  @Secured(value = STUDENTIN)
  public String showMySubmissions(final KeycloakAuthenticationToken token, final Model model) {

    final Account account = createAccountFromPrincipal(token);
    model.addAttribute(ACCOUNT_ATTRIBUTE, account);
    model.addAttribute(
        ALL_SUBMISSIONS_FROM_USER_ATTRIBUTE,
        submissionEventInfoService.getAllSubmissionsWithInfosByUser(account));

    return "user/mySubmissions";
  }

  @GetMapping("/download/template")
  @Secured(value = STUDENTIN)
  @ResponseBody
  public ResponseEntity<Resource> downloadTemplate(
      final KeycloakAuthenticationToken token, final Model model) {
    final Account account = createAccountFromPrincipal(token);

    model.addAttribute(ACCOUNT_ATTRIBUTE, account);
    final Resource file = new FileSystemResource(submissionTemplatePath);
    return ResponseEntity.ok()
        .header(
            HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
        .body(file);
  }

  @PostMapping(value = "/create-receipt/{submissionId}")
  @Secured(value = STUDENTIN)
  public String createReceipt(
      final KeycloakAuthenticationToken token,
      final Model model,
      @PathVariable("submissionId") final Long submissionId)
      throws SubmissionNotFoundException, UnauthorizedSubmissionAccessException {
    final Account account = createAccountFromPrincipal(token);
    model.addAttribute(ACCOUNT_ATTRIBUTE, account);

    final Submission submission =
        submissionAccessService.getAcceptedSubmissionIfAuthorized(submissionId, account);

    try {
      final Receipt receipt =
          receiptCreationAndStorageService.createReceiptAndSaveSignatureInDatabase(submission);
      receiptSendService.sendReceipt(receipt, account.getEmail());
    } catch (final UnrecoverableEntryException
        | NoSuchAlgorithmException
        | IOException
        | KeyStoreException
        | SignatureException
        | InvalidKeyException
        | EventNotFoundException
        | CertificateException e) {
      throw (HttpServerErrorException)
          new HttpServerErrorException(
                  HttpStatus.INTERNAL_SERVER_ERROR,
                  "Da scheint etwas schief gelaufen zu sein. Das tut uns leid :(")
              .initCause(e);
    } catch (final MessagingException e) {
      throw (HttpServerErrorException)
          new HttpServerErrorException(
                  HttpStatus.INTERNAL_SERVER_ERROR,
                  "Offenbar konnte die Nachricht nicht gesendet werden. Wenden Sie sich an einen Administrator.")
              .initCause(e);
    }
    return "user/receiptCreated";
  }
}
