package mops.hhu.de.rheinjug1.praxis.controller;

import static mops.hhu.de.rheinjug1.praxis.auth.RolesHelper.ORGA;
import static mops.hhu.de.rheinjug1.praxis.models.Account.createAccountFromPrincipal;
import static mops.hhu.de.rheinjug1.praxis.thymeleaf.ThymeleafAttributesHelper.ACCOUNT_ATTRIBUTE;
import static mops.hhu.de.rheinjug1.praxis.thymeleaf.ThymeleafAttributesHelper.ALL_SUBMISSIONS_ATTRIBUTE;

import io.minio.errors.MinioException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import mops.hhu.de.rheinjug1.praxis.exceptions.DuplicateSubmissionException;
import mops.hhu.de.rheinjug1.praxis.exceptions.EventNotFoundException;
import mops.hhu.de.rheinjug1.praxis.exceptions.SubmissionNotFoundException;
import mops.hhu.de.rheinjug1.praxis.models.Account;
import mops.hhu.de.rheinjug1.praxis.models.SubmissionEventInfo;
import mops.hhu.de.rheinjug1.praxis.models.SubmissionEventInfoDateComparator;
import mops.hhu.de.rheinjug1.praxis.services.MeetupService;
import mops.hhu.de.rheinjug1.praxis.services.SubmissionEventInfoService;
import mops.hhu.de.rheinjug1.praxis.services.submission.SubmissionAccessService;
import mops.hhu.de.rheinjug1.praxis.services.submission.SubmissionUploadService;
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

  private final SubmissionAccessService submissionAccessService;
  private final SubmissionUploadService submissionUploadService;
  private final SubmissionEventInfoService submissionEventInfoService;
  private final MeetupService meetupService;

  @GetMapping
  @Secured(value = ORGA)
  public String getAllSubmissions(final KeycloakAuthenticationToken token, final Model model) {
    final Account account = createAccountFromPrincipal(token);
    model.addAttribute(ACCOUNT_ATTRIBUTE, account);
    final List<SubmissionEventInfo> submissionEventInfos =
        submissionEventInfoService.getAllSubmissionsWithInfos();
    submissionEventInfos.sort(new SubmissionEventInfoDateComparator());
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

    final Account account = createAccountFromPrincipal(token);
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

    final Account account = createAccountFromPrincipal(token);
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

    final Account account = createAccountFromPrincipal(token);
    model.addAttribute(ACCOUNT_ATTRIBUTE, account);

    submissionUploadService.checkUploadable(meetupId, email);

    try {
      submissionUploadService.uploadToMinIoAndSaveSubmission(meetupId, file, name, email);
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
