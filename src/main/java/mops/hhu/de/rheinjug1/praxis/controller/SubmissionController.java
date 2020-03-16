package mops.hhu.de.rheinjug1.praxis.controller;

import static mops.hhu.de.rheinjug1.praxis.models.Account.createAccountFromPrincipal;
import static mops.hhu.de.rheinjug1.praxis.thymeleaf.ThymeleafAttributesHelper.ACCEPTED_SUBMISSIONS_ATTRIBUTE;
import static mops.hhu.de.rheinjug1.praxis.thymeleaf.ThymeleafAttributesHelper.ACCOUNT_ATTRIBUTE;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Optional;
import mops.hhu.de.rheinjug1.praxis.database.entities.AcceptedSubmission;
import mops.hhu.de.rheinjug1.praxis.exceptions.EventNotFoundException;
import mops.hhu.de.rheinjug1.praxis.models.Account;
import mops.hhu.de.rheinjug1.praxis.services.ReceiptService;
import mops.hhu.de.rheinjug1.praxis.services.SubmissionService;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/submissions")
public class SubmissionController {

  private final SubmissionService submissionService;

  private final ReceiptService receiptService;

  @Autowired
  public SubmissionController(
      final SubmissionService submissionService, final ReceiptService receiptService) {
    this.submissionService = submissionService;
    this.receiptService = receiptService;
  }

  @GetMapping
  @Secured("ROLE_studentin")
  public String getSubmissions(final KeycloakAuthenticationToken token, final Model model) {

    final Account account = createAccountFromPrincipal(token);
    model.addAttribute(ACCOUNT_ATTRIBUTE, account);
    model.addAttribute(
        ACCEPTED_SUBMISSIONS_ATTRIBUTE, submissionService.getAllAcceptedSubmissions(account));

    return "submissions";
  }

  @GetMapping(value = "/create-receipt/{submissionId}")
  @Secured("ROLE_studentin")
  public String createReceipt(
      final KeycloakAuthenticationToken token,
      final Model model,
      @PathVariable("submissionId") final Long submissionId) {
    final Account account = createAccountFromPrincipal(token);
    model.addAttribute(ACCOUNT_ATTRIBUTE, account);

    final Optional<AcceptedSubmission> acceptedSubmissionOptional =
        submissionService.getAcceptedSubmissionIfAuthorized(submissionId, account);
    if (acceptedSubmissionOptional.isEmpty()) {
      return "error";
    }

    final AcceptedSubmission acceptedSubmission = acceptedSubmissionOptional.get();
    try {
      receiptService.createReceiptAndSaveSignatureInDatabase(acceptedSubmission);
    } catch (final UnrecoverableEntryException
        | NoSuchAlgorithmException
        | IOException
        | KeyStoreException
        | SignatureException
        | InvalidKeyException
        | EventNotFoundException
        | CertificateException e) {
      return "error";
    }

    return "receiptCreated";
  }
}
