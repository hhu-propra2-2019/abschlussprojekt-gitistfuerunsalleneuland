package mops.hhu.de.rheinjug1.praxis.controller;

import static mops.hhu.de.rheinjug1.praxis.models.Account.createAccountFromPrincipal;
import static mops.hhu.de.rheinjug1.praxis.thymeleaf.ThymeleafAttributesHelper.ACCOUNT_ATTRIBUTE;
import static mops.hhu.de.rheinjug1.praxis.thymeleaf.ThymeleafAttributesHelper.MEETUP_ID_ATTRIBUTE;

import io.minio.errors.MinioException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import mops.hhu.de.rheinjug1.praxis.database.repositories.EventRepository;
import mops.hhu.de.rheinjug1.praxis.exceptions.DuplicateSubmissionException;
import mops.hhu.de.rheinjug1.praxis.exceptions.EventNotFoundException;
import mops.hhu.de.rheinjug1.praxis.models.Account;
import mops.hhu.de.rheinjug1.praxis.services.submission.SubmissionUploadService;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

@Controller
@AllArgsConstructor
@RequestMapping("/upload")
public class SubmissionUploadController {
  private final SubmissionUploadService submissionUploadService;
  private final EventRepository eventRepository;

  @GetMapping("/{meetupId}")
  @Secured("ROLE_studentin")
  public String showUploadForm(
      final KeycloakAuthenticationToken token, final Model model, @PathVariable final Long meetupId)
      throws EventNotFoundException, DuplicateSubmissionException {
    final Account account = createAccountFromPrincipal(token);
    final Event event = eventRepository.findById(meetupId).get();
    model.addAttribute(ACCOUNT_ATTRIBUTE, account);

    submissionUploadService.checkUploadable(meetupId, account);

    model.addAttribute("event",event);
    model.addAttribute(MEETUP_ID_ATTRIBUTE, meetupId);
    return "uploadForm";
  }

  @PostMapping("/{meetupId}")
  @Secured("ROLE_studentin")
  public String handleFileUpload(
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
        | InvalidKeyException
        | InterruptedException e) {
      e.printStackTrace();
    }

    return "redirect:/user/submissions";
  }
}
