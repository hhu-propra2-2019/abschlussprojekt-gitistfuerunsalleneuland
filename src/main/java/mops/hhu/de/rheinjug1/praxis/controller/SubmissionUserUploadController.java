package mops.hhu.de.rheinjug1.praxis.controller;

import static mops.hhu.de.rheinjug1.praxis.auth.RolesHelper.STUDENTIN;
import static mops.hhu.de.rheinjug1.praxis.models.Account.createAccountFromPrincipal;
import static mops.hhu.de.rheinjug1.praxis.thymeleaf.ThymeleafAttributesHelper.ACCOUNT_ATTRIBUTE;

import io.minio.errors.MinioException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import mops.hhu.de.rheinjug1.praxis.exceptions.DuplicateSubmissionException;
import mops.hhu.de.rheinjug1.praxis.exceptions.EventNotFoundException;
import mops.hhu.de.rheinjug1.praxis.models.Account;
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
@RequestMapping("/upload")
public class SubmissionUserUploadController {
  private final SubmissionUploadService submissionUploadService;

  @Secured(value = STUDENTIN)
  @GetMapping("/{meetupId}")
  public String showUploadForm(
      final KeycloakAuthenticationToken token, final Model model, @PathVariable final Long meetupId)
      throws EventNotFoundException, DuplicateSubmissionException {
    final Account account = createAccountFromPrincipal(token);
    final Event event = submissionUploadService.checkUploadableAndReturnEvent(meetupId, account);
    model.addAttribute(ACCOUNT_ATTRIBUTE, account);
    model.addAttribute("event", event);

    return "/user/uploadForm";
  }

  @Secured(value = STUDENTIN)
  @PostMapping("/{meetupId}")
  public ModelAndView handleFileUpload(
      final KeycloakAuthenticationToken token,
      final Model model,
      @RequestParam("file") final MultipartFile file,
      @PathVariable("meetupId") final Long meetupId) {

    final Account account = createAccountFromPrincipal(token);
    model.addAttribute(ACCOUNT_ATTRIBUTE, account);

    try {
      submissionUploadService.uploadToMinIoAndSaveSubmission(meetupId, file, account);
    } catch (final IOException
        | MinioException
        | XmlPullParserException
        | NoSuchAlgorithmException
        | InvalidKeyException e) {
      throw (HttpServerErrorException)
          new HttpServerErrorException(
                  HttpStatus.INTERNAL_SERVER_ERROR,
                  "Da scheint etwas schief gelaufen zu sein. Das tut uns leid :(")
              .initCause(e);
    }

    return new ModelAndView("redirect:/user/submissions");
  }
}
