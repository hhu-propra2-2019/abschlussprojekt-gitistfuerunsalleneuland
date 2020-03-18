package mops.hhu.de.rheinjug1.praxis.controller;

import static mops.hhu.de.rheinjug1.praxis.models.Account.createAccountFromPrincipal;
import static mops.hhu.de.rheinjug1.praxis.thymeleaf.ThymeleafAttributesHelper.*;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Optional;
import javax.mail.MessagingException;
import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.database.entities.Submission;
import mops.hhu.de.rheinjug1.praxis.exceptions.EventNotFoundException;
import mops.hhu.de.rheinjug1.praxis.models.Account;
import mops.hhu.de.rheinjug1.praxis.models.Receipt;
import mops.hhu.de.rheinjug1.praxis.services.receipt.ReceiptSendService;
import mops.hhu.de.rheinjug1.praxis.services.receipt.ReceiptService;
import mops.hhu.de.rheinjug1.praxis.services.submission.SubmissionService;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/user/submissions")
public class SubmissionUserController {

  private final SubmissionService submissionService;
  private final ReceiptService receiptService;
  private final ReceiptSendService receiptSendService;

  @GetMapping
  @Secured("ROLE_studentin")
  public String showMySubmissions(final KeycloakAuthenticationToken token, final Model model) {

    final Account account = createAccountFromPrincipal(token);
    model.addAttribute(ACCOUNT_ATTRIBUTE, account);
    model.addAttribute(
        ALL_SUBMISSIONS_FROM_USER_ATTRIBUTE, submissionService.getAllSubmissionsByUser(account));

    return "user/mySubmissions";
  }

  @PostMapping(value = "/create-receipt/{submissionId}")
  @Secured("ROLE_studentin")
  public String createReceipt(
      final KeycloakAuthenticationToken token,
      final Model model,
      @PathVariable("submissionId") final Long submissionId) {
    final Account account = createAccountFromPrincipal(token);
    model.addAttribute(ACCOUNT_ATTRIBUTE, account);

    final Optional<Submission> acceptedSubmissionOptional =
        submissionService.getAcceptedSubmissionIfAuthorized(submissionId, account);
    if (acceptedSubmissionOptional.isEmpty()) {
      return "error";
    }

    final Submission submission = acceptedSubmissionOptional.get();
    try {
      final Receipt receipt = receiptService.createReceiptAndSaveSignatureInDatabase(submission);
      receiptSendService.sendReceipt(receipt, account.getEmail());
    } catch (final UnrecoverableEntryException
        | NoSuchAlgorithmException
        | IOException
        | KeyStoreException
        | SignatureException
        | InvalidKeyException
        | EventNotFoundException
        | CertificateException e) {
      return "internalServerError";
    } catch (final MessagingException e) {
      return "messageCantBeSentError";
    }
    return "user/receiptCreated";
  }
}
