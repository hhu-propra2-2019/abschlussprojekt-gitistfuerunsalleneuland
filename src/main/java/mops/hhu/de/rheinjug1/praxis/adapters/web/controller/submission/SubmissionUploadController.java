
package mops.hhu.de.rheinjug1.praxis.adapters.web.controller.submission;

import io.minio.errors.MinioException;
import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.domain.Account;
import mops.hhu.de.rheinjug1.praxis.domain.AccountFactory;
import mops.hhu.de.rheinjug1.praxis.domain.event.Event;
import mops.hhu.de.rheinjug1.praxis.domain.event.EventNotFoundException;
import mops.hhu.de.rheinjug1.praxis.adapters.database.event.EventBackendRepo;
import mops.hhu.de.rheinjug1.praxis.domain.event.EventRepository;
import mops.hhu.de.rheinjug1.praxis.domain.submission.UploadService;
import mops.hhu.de.rheinjug1.praxis.domain.submission.exception.DuplicateSubmissionException;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static mops.hhu.de.rheinjug1.praxis.adapters.web.thymeleaf.ThymeleafAttributesHelper.*;

@Controller
@AllArgsConstructor
@RequestMapping("/upload")
public class SubmissionUploadController {
  private final UploadService uploadService;
  private final AccountFactory accountFactory;
  private final EventRepository eventRepository;

  @GetMapping("/{meetupId}")
  @Secured("ROLE_studentin")
  public String showUploadForm(
      final KeycloakAuthenticationToken token,
      final Model model,
      @ModelAttribute(UPLOAD_ERROR_ATTRIBUTE) final String uploadError,
      @PathVariable final Long meetupId)
      throws EventNotFoundException, DuplicateSubmissionException {
    final Account account = accountFactory.createFromPrincipal(token);
    final Event event = eventRepository.findById(meetupId).get();
    model.addAttribute(ACCOUNT_ATTRIBUTE, account);
      model.addAttribute(UPLOAD_ERROR_ATTRIBUTE, uploadError);

      final String meetupTitle =
              uploadService.checkUploadableAndReturnTitle(meetupId, account);

      model.addAttribute("event", event);
      model.addAttribute(MEETUP_ID_ATTRIBUTE, meetupId);
      model.addAttribute(MEETUP_TITLE_ATTRIBUTE, meetupTitle);
    return "uploadForm";
  }

  @PostMapping("/{meetupId}")
  @Secured("ROLE_studentin")
  public ModelAndView handleFileUpload(
      final KeycloakAuthenticationToken token,
      final Model model,
      final RedirectAttributes attributes,
      @RequestParam("file") final MultipartFile file,
      @PathVariable("meetupId") final Long meetupId) {
    final Account account = accountFactory.createFromPrincipal(token);
    model.addAttribute(ACCOUNT_ATTRIBUTE, account);

    if (file.isEmpty()) {
      attributes.addAttribute(UPLOAD_ERROR_ATTRIBUTE, NO_FILE_UPLOAD_ERROR);
      return new ModelAndView(String.format("redirect:/upload/%d", meetupId));
    }

    try {
      uploadService.uploadAndSaveSubmission(meetupId, file, account);
    } catch (final IOException | MinioException | XmlPullParserException | NoSuchAlgorithmException | InvalidKeyException | InterruptedException e) {
      e.printStackTrace();
    }

    return new ModelAndView("redirect:/user/submissions");
  }
}
