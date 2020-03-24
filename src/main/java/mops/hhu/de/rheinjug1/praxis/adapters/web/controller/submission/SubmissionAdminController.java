package mops.hhu.de.rheinjug1.praxis.adapters.web.controller.submission;

import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.domain.Account;
import mops.hhu.de.rheinjug1.praxis.domain.AccountFactory;
import mops.hhu.de.rheinjug1.praxis.domain.submission.SubmissionAccessService;
import mops.hhu.de.rheinjug1.praxis.domain.submission.eventinfo.SubmissionEventInfo;
import mops.hhu.de.rheinjug1.praxis.domain.submission.eventinfo.SubmissionEventInfoRepository;
import mops.hhu.de.rheinjug1.praxis.domain.submission.exception.SubmissionNotFoundException;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static mops.hhu.de.rheinjug1.praxis.adapters.web.thymeleaf.ThymeleafAttributesHelper.ACCOUNT_ATTRIBUTE;
import static mops.hhu.de.rheinjug1.praxis.adapters.web.thymeleaf.ThymeleafAttributesHelper.ALL_SUBMISSIONS_ATTRIBUTE;

@Controller
@AllArgsConstructor
@RequestMapping("/admin/submissions")
public class SubmissionAdminController {

  private final AccountFactory accountFactory;
  private final SubmissionAccessService submissionAccessService;
  private final SubmissionEventInfoRepository submissionEventInfoRepository;

  @GetMapping
  @Secured("ROLE_orga")
  public String getAllSubmissions(final KeycloakAuthenticationToken token, final Model model) {
    final Account account = accountFactory.createFromPrincipal(token);
    model.addAttribute(ACCOUNT_ATTRIBUTE, account);
    final List<SubmissionEventInfo> submissionEventInfos =
        submissionEventInfoRepository.getAllSubmissionsWithInfosSorted();
    model.addAttribute(ALL_SUBMISSIONS_ATTRIBUTE, submissionEventInfos);
    return "/admin/allSubmissions";
  }

  @PostMapping(value = "/accept/{submissionId}")
  @Secured("ROLE_orga")
  public String acceptSubmission(
      final KeycloakAuthenticationToken token,
      final Model model,
      @PathVariable("submissionId") final Long submissionId)
      throws SubmissionNotFoundException {

    final Account account = accountFactory.createFromPrincipal(token);
    model.addAttribute(ACCOUNT_ATTRIBUTE, account);

    submissionAccessService.accept(submissionId);

    return "redirect:/admin/submissions";
  }
}
