package mops.hhu.de.rheinjug1.praxis.adapters.web.controller.submission;

import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.adapters.auth.AccountImpl;
import mops.hhu.de.rheinjug1.praxis.adapters.web.thymeleaf.ThymeleafAttributesHelper;
import mops.hhu.de.rheinjug1.praxis.domain.Account;
import mops.hhu.de.rheinjug1.praxis.domain.AccountFactory;
import mops.hhu.de.rheinjug1.praxis.domain.submission.SubmissionService;
import mops.hhu.de.rheinjug1.praxis.domain.submission.exception.SubmissionNotFoundException;
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

  private final SubmissionService submissionService;
  private final AccountFactory accountFactory;

  @GetMapping
  @Secured("ROLE_orga")
  public String getAllSubmissions(final KeycloakAuthenticationToken token, final Model model) {
    final Account account = accountFactory.createFromToken(token);
    model.addAttribute(ThymeleafAttributesHelper.ACCOUNT_ATTRIBUTE, account);
    model.addAttribute(ThymeleafAttributesHelper.ALL_SUBMISSIONS_ATTRIBUTE, submissionService.getAllSubmissions());
    return "/admin/allSubmissions";
  }

  @PostMapping(value = "/accept/{submissionId}")
  @Secured("ROLE_orga")
  public String acceptSubmission(
      final KeycloakAuthenticationToken token,
      final Model model,
      @PathVariable("submissionId") final Long submissionId)
      throws SubmissionNotFoundException {

    final Account account = accountFactory.createFromToken(token);
    model.addAttribute(ThymeleafAttributesHelper.ACCOUNT_ATTRIBUTE, account);

    submissionService.accept(submissionId);

    return "redirect:/admin/submissions";
  }
}
