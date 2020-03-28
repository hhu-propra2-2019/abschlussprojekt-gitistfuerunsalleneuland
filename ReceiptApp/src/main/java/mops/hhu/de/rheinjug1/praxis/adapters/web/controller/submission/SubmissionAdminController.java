package mops.hhu.de.rheinjug1.praxis.adapters.web.controller.submission;

import static mops.hhu.de.rheinjug1.praxis.adapters.auth.RolesHelper.ORGA;
import static mops.hhu.de.rheinjug1.praxis.adapters.web.thymeleaf.ThymeleafAttributesHelper.ACCOUNT_ATTRIBUTE;
import static mops.hhu.de.rheinjug1.praxis.adapters.web.thymeleaf.ThymeleafAttributesHelper.ALL_SUBMISSIONS_ATTRIBUTE;

import io.minio.errors.MinioException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.domain.Account;
import mops.hhu.de.rheinjug1.praxis.domain.AccountFactory;
import mops.hhu.de.rheinjug1.praxis.domain.event.Event;
import mops.hhu.de.rheinjug1.praxis.domain.event.EventNotFoundException;
import mops.hhu.de.rheinjug1.praxis.domain.event.MeetupService;
import mops.hhu.de.rheinjug1.praxis.domain.submission.SubmissionAccessService;
import mops.hhu.de.rheinjug1.praxis.domain.submission.UploadService;
import mops.hhu.de.rheinjug1.praxis.domain.submission.eventinfo.SubmissionEventInfo;
import mops.hhu.de.rheinjug1.praxis.domain.submission.eventinfo.SubmissionEventInfoDomainRepository;
import mops.hhu.de.rheinjug1.praxis.domain.submission.exception.DuplicateSubmissionException;
import mops.hhu.de.rheinjug1.praxis.domain.submission.exception.SubmissionNotFoundException;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.xmlpull.v1.XmlPullParserException;

@Controller
@AllArgsConstructor
@RequestMapping("/admin/submissions")
public class SubmissionAdminController {

  private final AccountFactory accountFactory;
  private final SubmissionAccessService submissionAccessService;
  private final SubmissionEventInfoDomainRepository submissionEventInfoDomainRepository;
  private final UploadService uploadService;
  private final MeetupService meetupService;

  @GetMapping
  @Secured(value = ORGA)
  public String getAllSubmissions(final KeycloakAuthenticationToken token, final Model model) {
    final Account account = accountFactory.createFromPrincipal(token);
    model.addAttribute(ACCOUNT_ATTRIBUTE, account);
    final List<SubmissionEventInfo> submissionEventInfos =
        submissionEventInfoDomainRepository.getAllSubmissionsWithInfosSorted();
    model.addAttribute(ALL_SUBMISSIONS_ATTRIBUTE, submissionEventInfos);
    return "/admin/allSubmissions";
  }

  @PostMapping(value = "/accept/{submissionId}")
  @Secured(value = ORGA)
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

  @GetMapping(value = "/upload/{meetupId}")
  @Secured(value = ORGA)
  public String showUploadForm(
      final KeycloakAuthenticationToken token,
      final Model model,
      @PathVariable("meetupId") final Long meetupId)
      throws EventNotFoundException {

    final Account account = accountFactory.createFromPrincipal(token);
    model.addAttribute(ACCOUNT_ATTRIBUTE, account);

    final Event event = meetupService.getEventIfExistent(meetupId);

    model.addAttribute("event", event);

    return "/admin/uploadForm";
  }

  @PostMapping(value = "/upload/{meetupId}")
  @Secured(value = ORGA)
  public ModelAndView uploadSubmission(
      final KeycloakAuthenticationToken token,
      final Model model,
      @RequestParam final String name,
      @RequestParam final String email,
      @RequestParam("file") final MultipartFile file,
      @PathVariable("meetupId") final Long meetupId)
      throws DuplicateSubmissionException, EventNotFoundException {

    final Account account = accountFactory.createFromPrincipal(token);
    model.addAttribute(ACCOUNT_ATTRIBUTE, account);

    uploadService.checkUploadable(meetupId, email);

    try {
      uploadService.uploadAndSaveSubmission(meetupId, file, name, email);
    } catch (final MinioException
        | XmlPullParserException
        | NoSuchAlgorithmException
        | InvalidKeyException
        | IOException e) {
      throw (HttpServerErrorException)
          new HttpServerErrorException(
                  HttpStatus.INTERNAL_SERVER_ERROR,
                  "Da scheint etwas schief gelaufen zu sein. Das tut uns leid :(")
              .initCause(e);
    }

    return new ModelAndView("redirect:/admin/submissions");
  }
}
