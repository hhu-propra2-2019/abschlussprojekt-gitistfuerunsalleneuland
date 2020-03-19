package mops.hhu.de.rheinjug1.praxis.controller;

import static mops.hhu.de.rheinjug1.praxis.models.Account.createAccountFromPrincipal;
import static mops.hhu.de.rheinjug1.praxis.thymeleaf.ThymeleafAttributesHelper.ACCOUNT_ATTRIBUTE;
import static mops.hhu.de.rheinjug1.praxis.thymeleaf.ThymeleafAttributesHelper.ALL_SUBMISSIONS_ATTRIBUTE;

import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.exceptions.SubmissionNotFoundException;
import mops.hhu.de.rheinjug1.praxis.models.Account;
import mops.hhu.de.rheinjug1.praxis.services.submission.SubmissionAccessService;
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
@RequestMapping("/admin/submissions")
public class SubmissionAdminController {

  private final SubmissionAccessService submissionAccessService;

  @GetMapping
  @Secured("ROLE_orga")
  public String getAllSubmissions(final KeycloakAuthenticationToken token, final Model model) {
    final Account account = createAccountFromPrincipal(token);
    model.addAttribute(ACCOUNT_ATTRIBUTE, account);
    model.addAttribute(ALL_SUBMISSIONS_ATTRIBUTE, submissionAccessService.getAllSubmissions());
    return "/admin/allSubmissions";
  }

  @PostMapping(value = "/accept/{submissionId}")
  @Secured("ROLE_orga")
  public String acceptSubmission(
      final KeycloakAuthenticationToken token,
      final Model model,
      @PathVariable("submissionId") final Long submissionId)
      throws SubmissionNotFoundException {

    final Account account = createAccountFromPrincipal(token);
    model.addAttribute(ACCOUNT_ATTRIBUTE, account);

    submissionAccessService.accept(submissionId);

    return "redirect:/admin/submissions";
  }
}
