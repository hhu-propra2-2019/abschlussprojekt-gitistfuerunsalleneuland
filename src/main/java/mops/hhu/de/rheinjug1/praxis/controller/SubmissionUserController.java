package mops.hhu.de.rheinjug1.praxis.controller;

import static mops.hhu.de.rheinjug1.praxis.models.Account.createAccountFromPrincipal;
import static mops.hhu.de.rheinjug1.praxis.thymeleaf.ThymeleafAttributesHelper.ACCOUNT_ATTRIBUTE;
import static mops.hhu.de.rheinjug1.praxis.thymeleaf.ThymeleafAttributesHelper.ALL_SUBMISSIONS_FROM_USER_ATTRIBUTE;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import javax.mail.MessagingException;
import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.database.entities.Submission;
import mops.hhu.de.rheinjug1.praxis.exceptions.EventNotFoundException;
import mops.hhu.de.rheinjug1.praxis.models.Account;
import mops.hhu.de.rheinjug1.praxis.models.Receipt;
import mops.hhu.de.rheinjug1.praxis.services.SubmissionEventInfoService;
import mops.hhu.de.rheinjug1.praxis.services.receipt.ReceiptCreationAndStorageService;
import mops.hhu.de.rheinjug1.praxis.services.receipt.ReceiptSendService;
import mops.hhu.de.rheinjug1.praxis.services.submission.SubmissionAccessService;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpServerErrorException;

@Controller
@AllArgsConstructor
@RequestMapping("/user/submissions")
public class SubmissionUserController {

  private final SubmissionAccessService submissionAccessService;
  private final ReceiptCreationAndStorageService receiptCreationAndStorageService;
  private final ReceiptSendService receiptSendService;
  private final SubmissionEventInfoService submissionEventInfoService;

  @GetMapping
  @Secured("ROLE_studentin")
  public String showMySubmissions(final KeycloakAuthenticationToken token, final Model model) {

    final Account account = createAccountFromPrincipal(token);
    model.addAttribute(ACCOUNT_ATTRIBUTE, account);
    model.addAttribute(
        ALL_SUBMISSIONS_FROM_USER_ATTRIBUTE,
        submissionEventInfoService.getAllSubmissionsWithInfosByUser(account));

    return "user/mySubmissions";
  }

  @PostMapping(value = "/create-receipt/{submissionId}")
  @Secured("ROLE_studentin")
  public String createReceipt(
      final KeycloakAuthenticationToken token,
      final Model model,
      @PathVariable("submissionId") final Long submissionId)
      throws Throwable {
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
