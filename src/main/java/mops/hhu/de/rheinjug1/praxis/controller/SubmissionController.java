package mops.hhu.de.rheinjug1.praxis.controller;

import static mops.hhu.de.rheinjug1.praxis.models.Account.createAccountFromPrincipal;
import static mops.hhu.de.rheinjug1.praxis.thymeleaf.ThymeleafAttributesHelper.*;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import javax.mail.MessagingException;
import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.database.entities.Submission;
import mops.hhu.de.rheinjug1.praxis.exceptions.EventNotFoundException;
import mops.hhu.de.rheinjug1.praxis.exceptions.SubmissionNotFoundException;
import mops.hhu.de.rheinjug1.praxis.exceptions.UnauthorizedSubmissionAccessException;
import mops.hhu.de.rheinjug1.praxis.models.Account;
import mops.hhu.de.rheinjug1.praxis.models.Receipt;
import mops.hhu.de.rheinjug1.praxis.services.receipt.ReceiptCreationAndStorageService;
import mops.hhu.de.rheinjug1.praxis.services.receipt.ReceiptSendService;
import mops.hhu.de.rheinjug1.praxis.services.submission.SubmissionService;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/submissions")
public class SubmissionController {

  private final SubmissionService submissionService;
  private final ReceiptCreationAndStorageService receiptService;
  private final ReceiptSendService receiptSendService;

  @GetMapping
  @Secured("ROLE_orga")
  public String getAllSubmissions(final KeycloakAuthenticationToken token, final Model model) {

    final Account account = createAccountFromPrincipal(token);
    model.addAttribute(ACCOUNT_ATTRIBUTE, account);
    model.addAttribute(ALL_SUBMISSIONS_ATTRIBUTE, submissionService.getAllSubmissions());
    return "allSubmissions";
  }

  @GetMapping(value = "/accept/{submissionId}")
  @Secured("ROLE_orga")
  public String acceptSubmission(
      final KeycloakAuthenticationToken token,
      final Model model,
      @PathVariable("submissionId") final Long submissionId)
      throws SubmissionNotFoundException {

    final Account account = createAccountFromPrincipal(token);
    model.addAttribute(ACCOUNT_ATTRIBUTE, account);

    submissionService.accept(submissionId);

    return "redirect:/submissions";
  }

  @GetMapping("/user")
  @Secured("ROLE_studentin")
  public String getSubmissions(final KeycloakAuthenticationToken token, final Model model) {

    final Account account = createAccountFromPrincipal(token);
    model.addAttribute(ACCOUNT_ATTRIBUTE, account);
    model.addAttribute(
        ALL_SUBMISSIONS_FROM_USER_ATTRIBUTE,
        submissionService.getAllSubmissionsWithInfosByUser(account));

    return "mySubmissions";
  }

  @GetMapping(value = "/create-receipt/{submissionId}")
  @Secured("ROLE_studentin")
  public String createReceipt(
      final KeycloakAuthenticationToken token,
      final Model model,
      @PathVariable("submissionId") final Long submissionId)
      throws SubmissionNotFoundException, UnauthorizedSubmissionAccessException {
    final Account account = createAccountFromPrincipal(token);
    model.addAttribute(ACCOUNT_ATTRIBUTE, account);

    Submission submission;
    try {
      submission = submissionService.getAcceptedSubmissionIfAuthorized(submissionId, account).get();
    } catch (SubmissionNotFoundException | UnauthorizedSubmissionAccessException e1) {
      e1.printStackTrace();
      return "error";
    }

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

    return "receiptCreated";
  }
}
