package mops.hhu.de.rheinjug1.praxis.adapters.minio;

import io.minio.errors.MinioException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.domain.Account;
import mops.hhu.de.rheinjug1.praxis.domain.event.Event;
import mops.hhu.de.rheinjug1.praxis.domain.event.EventNotFoundException;
import mops.hhu.de.rheinjug1.praxis.domain.event.EventRepository;
import mops.hhu.de.rheinjug1.praxis.domain.event.MeetupService;
import mops.hhu.de.rheinjug1.praxis.domain.submission.Submission;
import mops.hhu.de.rheinjug1.praxis.domain.submission.SubmissionRepository;
import mops.hhu.de.rheinjug1.praxis.domain.submission.UploadService;
import mops.hhu.de.rheinjug1.praxis.domain.submission.exception.DuplicateSubmissionException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

@Service
@AllArgsConstructor
public class UploadServiceImpl implements UploadService {

  private final SubmissionRepository submissionRepository;
  private final MeetupService meetupService;
  private final MinIoUploadService minIoUploadService;
  private final MinIoDownloadService minIoDownloadService;
  private final EventRepository eventRepository;

  @Override
  public void uploadAndSaveSubmission(
      final Long meetupId, final MultipartFile file, final Account account)
      throws MinioException, XmlPullParserException, NoSuchAlgorithmException, InvalidKeyException,
          IOException {
    uploadAndSaveSubmission(meetupId, file, account.getName(), account.getEmail());

  }

  @Override
  public Event checkUploadableAndReturnEvent(final Long meetupId, final Account account)
      throws EventNotFoundException, DuplicateSubmissionException {

    final Event event = meetupService.getEventIfExistent(meetupId);

    if (existsByMeetupIdAndEmail(meetupId, account.getEmail())) {
      throw new DuplicateSubmissionException(meetupId, account.getEmail());
    }
    return event;
  }

  private static String generateFileName(final Long meetupId, final String email) {
    final String sanitizedEmail = email.replace("@", "_").replace(".", "_");
    return String.format("Zusammenfassung-%d-%s.txt", meetupId, sanitizedEmail);
  }

  @Override
  public void checkUploadable(final Long meetupId, final String email)
      throws DuplicateSubmissionException, EventNotFoundException {

    final Event event = meetupService.getEventIfExistent(meetupId);

    if (existsByMeetupIdAndEmail(event.getId(), email)) {
      throw new DuplicateSubmissionException(event.getId(), email);
    }
  }

    @Override
    public void uploadAndSaveSubmission(Long meetupId, MultipartFile file, String name, String email) throws MinioException, XmlPullParserException, NoSuchAlgorithmException, InvalidKeyException, IOException {

      final String fileName = generateFileName(meetupId, email);

      minIoUploadService.transferMultipartFileToMinIo(file, fileName);
      final String minIoLink = minIoDownloadService.getURLforDownload(fileName);
      final Submission newSubmission =
              Submission.builder()
                      .accepted(false)
                      .email(email)
                      .name(name)
                      .meetupId(meetupId)
                      .minIoLink(minIoLink)
                      .build();
      submissionRepository.save(newSubmission);
    }

    private boolean existsByMeetupIdAndEmail(final Long meetupId, final String email) {
    return !submissionRepository.findAllByMeetupIdAndEmail(meetupId, email).isEmpty();
  }
}
