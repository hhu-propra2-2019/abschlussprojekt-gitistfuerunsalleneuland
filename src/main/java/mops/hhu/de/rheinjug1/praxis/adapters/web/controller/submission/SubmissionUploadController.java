package mops.hhu.de.rheinjug1.praxis.adapters.web.controller.submission;

import io.minio.errors.MinioException;
import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.adapters.web.thymeleaf.ThymeleafAttributesHelper;
import mops.hhu.de.rheinjug1.praxis.domain.Account;
import mops.hhu.de.rheinjug1.praxis.domain.AccountFactory;
import mops.hhu.de.rheinjug1.praxis.domain.event.EventNotFoundException;
import mops.hhu.de.rheinjug1.praxis.domain.submission.UploadService;
import mops.hhu.de.rheinjug1.praxis.domain.submission.exception.DuplicateSubmissionException;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Controller
@AllArgsConstructor
@RequestMapping("/upload")
public class SubmissionUploadController {
  private final UploadService uploadService;
  private final AccountFactory accountFactory;

  @GetMapping("/{meetupId}")
  @Secured("ROLE_studentin")
  public String showUploadForm(
      final KeycloakAuthenticationToken token, final Model model, @PathVariable final Long meetupId)
      throws EventNotFoundException, DuplicateSubmissionException {
    final Account account = accountFactory.createFromToken(token);
    model.addAttribute(ThymeleafAttributesHelper.ACCOUNT_ATTRIBUTE, account);

    uploadService.checkUploadable(meetupId, account);

    model.addAttribute(ThymeleafAttributesHelper.MEETUP_ID_ATTRIBUTE, meetupId);
    return "uploadForm";
  }

  @PostMapping("/{meetupId}")
  @Secured("ROLE_studentin")
  public String handleFileUpload(
      final KeycloakAuthenticationToken token,
      final Model model,
      @RequestParam("file") final MultipartFile file,
      @PathVariable("meetupId") final Long meetupId) {
    final Account account = accountFactory.createFromToken(token);
    model.addAttribute(ThymeleafAttributesHelper.ACCOUNT_ATTRIBUTE, account);

    try {
      uploadService.uploadAndSaveSubmission(meetupId, file, account);
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
