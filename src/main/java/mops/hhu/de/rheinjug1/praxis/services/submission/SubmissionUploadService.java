package mops.hhu.de.rheinjug1.praxis.services.submission;

import io.minio.errors.MinioException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import mops.hhu.de.rheinjug1.praxis.database.entities.Submission;
import mops.hhu.de.rheinjug1.praxis.database.repositories.EventRepository;
import mops.hhu.de.rheinjug1.praxis.database.repositories.SubmissionRepository;
import mops.hhu.de.rheinjug1.praxis.exceptions.DuplicateSubmissionException;
import mops.hhu.de.rheinjug1.praxis.exceptions.EventNotFoundException;
import mops.hhu.de.rheinjug1.praxis.models.Account;
import mops.hhu.de.rheinjug1.praxis.services.minio.MinIoDownloadService;
import mops.hhu.de.rheinjug1.praxis.services.minio.MinIoUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

@Service
public class SubmissionUploadService {

  private final SubmissionRepository submissionRepository;
  private final MinIoUploadService minIoUploadService;
  private final MinIoDownloadService minIoDownloadService;
  private final EventRepository eventRepository;

  @Autowired
  public SubmissionUploadService(
      final SubmissionRepository submissionRepository,
      final MinIoUploadService minIoUploadService,
      final MinIoDownloadService minIoDownloadService,
      final EventRepository eventRepository) {
    this.submissionRepository = submissionRepository;
    this.minIoUploadService = minIoUploadService;
    this.minIoDownloadService = minIoDownloadService;
    this.eventRepository = eventRepository;
  }

  public void uploadToMinIoAndSaveSubmission(
      final Long meetupId, final MultipartFile file, final Account account)
      throws MinioException, XmlPullParserException, NoSuchAlgorithmException, InvalidKeyException,
          IOException {

    final String fileName = generateFileName(meetupId, account.getEmail());

    minIoUploadService.transferMultipartFileToMinIo(file, fileName);
    final String minIoLink = minIoDownloadService.getURLforObjectDownload(fileName);
    final Submission newSubmission =
        Submission.builder()
            .accepted(false)
            .email(account.getEmail())
            .name(account.getName())
            .meetupId(meetupId)
            .minIoLink(minIoLink)
            .build();
    submissionRepository.save(newSubmission);
  }

  private static String generateFileName(final Long meetupId, final String email) {
    final String sanitizedEmail = email.replace("@", "_").replace(".", "_");
    return String.format("Zusammenfassung-%d-%s.txt", meetupId, sanitizedEmail);
  }

  public void checkUploadable(final Long meetupId, final Account account)
      throws EventNotFoundException, DuplicateSubmissionException {
    if (!eventRepository.existsById(meetupId)) {
      throw new EventNotFoundException(meetupId);
    }

    if (existsByMeetupIdAndEmail(meetupId, account.getEmail())) {
      throw new DuplicateSubmissionException(meetupId, account.getEmail());
    }
  }

  private boolean existsByMeetupIdAndEmail(final Long meetupId, final String email) {
    return !submissionRepository.findAllByMeetupIdAndEmail(meetupId, email).isEmpty();
  }
}
