package mops.hhu.de.rheinjug1.praxis.adapters.web.controller.submission;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Optional;
import javax.mail.MessagingException;
import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.adapters.web.thymeleaf.ThymeleafAttributesHelper;
import mops.hhu.de.rheinjug1.praxis.domain.Account;
import mops.hhu.de.rheinjug1.praxis.domain.AccountFactory;
import mops.hhu.de.rheinjug1.praxis.domain.event.EventNotFoundException;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.Receipt;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.ReceiptCreationAndStorageService;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.ReceiptSendService;
import mops.hhu.de.rheinjug1.praxis.domain.submission.Submission;
import mops.hhu.de.rheinjug1.praxis.domain.submission.SubmissionService;
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

  private final SubmissionService submissionService;
  private final ReceiptCreationAndStorageService receiptCreationAndStorageService;
  private final ReceiptSendService receiptSendService;
  private final AccountFactory accountFactory;

  @GetMapping
  @Secured("ROLE_studentin")
  public String showMySubmissions(final KeycloakAuthenticationToken token, final Model model) {

    final Account account = accountFactory.createFromToken(token);
    model.addAttribute(ThymeleafAttributesHelper.ACCOUNT_ATTRIBUTE, account);
    model.addAttribute(
        ThymeleafAttributesHelper.ALL_SUBMISSIONS_FROM_USER_ATTRIBUTE,
        submissionService.getAllSubmissionsWithInfosByUser(account));

    return "user/mySubmissions";
  }

  @PostMapping(value = "/create-receipt/{submissionId}")
  @Secured("ROLE_studentin")
  public String createReceipt(
      final KeycloakAuthenticationToken token,
      final Model model,
      @PathVariable("submissionId") final Long submissionId)
      throws Throwable {
    final Account account = accountFactory.createFromToken(token);
    model.addAttribute(ThymeleafAttributesHelper.ACCOUNT_ATTRIBUTE, account);

    final Optional<Submission> submission =
        submissionService.getAcceptedSubmissionIfAuthorized(submissionId, account);

    try {
      final Receipt receipt =
          receiptCreationAndStorageService.createReceiptAndSaveSignatureInDatabase(
              submission.get());
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
