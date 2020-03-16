package mops.hhu.de.rheinjug1.praxis.controller;

import static mops.hhu.de.rheinjug1.praxis.models.Account.createAccountFromPrincipal;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
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

  @GetMapping(value = "/create-receipt/{submissionId}")
  @Secured("ROLE_studentin")
  public String createReceipt(
      final KeycloakAuthenticationToken token,
      final Model model,
      @PathVariable("submissionId") final Long submissionId) {
    Account account = new Account();
    if (token != null) {
      account = createAccountFromPrincipal(token);
      model.addAttribute(ACCOUNT_ATTRIBUTE, createAccountFromPrincipal(token));
    }
    final Optional<AcceptedSubmission> acceptedSubmissionOptional =
        submissionService.validateSubmission(submissionId, account);
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

    return "receipt created";
  }
}
