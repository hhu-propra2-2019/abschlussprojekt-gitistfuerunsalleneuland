package mops.hhu.de.rheinjug1.praxis.adapters.minio;

import io.minio.errors.MinioException;
import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.domain.Account;
import mops.hhu.de.rheinjug1.praxis.domain.event.Event;
import mops.hhu.de.rheinjug1.praxis.domain.event.EventNotFoundException;
import mops.hhu.de.rheinjug1.praxis.domain.event.MeetupService;
import mops.hhu.de.rheinjug1.praxis.domain.submission.Submission;
import mops.hhu.de.rheinjug1.praxis.domain.submission.SubmissionRepository;
import mops.hhu.de.rheinjug1.praxis.domain.submission.UploadService;
import mops.hhu.de.rheinjug1.praxis.domain.submission.exception.DuplicateSubmissionException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@AllArgsConstructor
public class UploadServiceImpl implements UploadService {

  private final SubmissionRepository submissionRepository;
  private final MeetupService meetupService;
  private final MinIoUploadService minIoUploadService;
  private final MinIoDownloadService minIoDownloadService;

  @Override
  public void uploadAndSaveSubmission(
      final Long meetupId, final MultipartFile file, final Account account)
      throws MinioException, XmlPullParserException, NoSuchAlgorithmException, InvalidKeyException,
          IOException {

    final String fileName = generateFileName(meetupId, account.getEmail());

    minIoUploadService.transferMultipartFileToMinIo(file, fileName);
    final String minIoLink = minIoDownloadService.getURLforDownload(fileName);
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
    final String sanitizedEmail = email.replace("@", "_")
            .replace(".", "_");
    return String.format("Zusammenfassung-%d-%s.txt", meetupId, sanitizedEmail);
  }

  @Override
  public String checkUploadableAndReturnTitle(final Long meetupId, final Account account)
      throws EventNotFoundException, DuplicateSubmissionException {

    final Event event = meetupService.getEventIfExistent(meetupId);

    if (existsByMeetupIdAndEmail(meetupId, account.getEmail())) {
      throw new DuplicateSubmissionException(meetupId, account.getEmail());
    }
    return event.getName();
  }

  private boolean existsByMeetupIdAndEmail(final Long meetupId, final String email) {
    return !submissionRepository.findAllByMeetupIdAndEmail(meetupId, email).isEmpty();
  }
}
