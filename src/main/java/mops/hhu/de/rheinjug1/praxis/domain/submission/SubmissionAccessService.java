package mops.hhu.de.rheinjug1.praxis.domain.submission;

import java.util.Optional;
import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.domain.Account;
import mops.hhu.de.rheinjug1.praxis.domain.TimeFormatService;
import mops.hhu.de.rheinjug1.praxis.domain.submission.exception.SubmissionNotFoundException;
import mops.hhu.de.rheinjug1.praxis.domain.submission.exception.UnauthorizedSubmissionAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubmissionAccessService {

  private final SubmissionRepository submissionRepository;
  private final TimeFormatService timeFormatService;

  public Submission getAcceptedSubmissionIfAuthorized(
      final Long submissionId, final Account account)
      throws SubmissionNotFoundException, UnauthorizedSubmissionAccessException {

    final Optional<Submission> acceptedSubmissionOptional =
        submissionRepository.findById(submissionId);

    if (acceptedSubmissionOptional.isEmpty()) {
      throw new SubmissionNotFoundException(submissionId);
    }

    final Submission submission = acceptedSubmissionOptional.get();

    if (!submission.isFromUser(account)) {
      throw new UnauthorizedSubmissionAccessException(submissionId);
    }

    return submission;
  }

  public void accept(final Long submissionId) throws SubmissionNotFoundException {
    final Optional<Submission> oldSubmissionOptional = submissionRepository.findById(submissionId);

    if (oldSubmissionOptional.isEmpty()) {
      throw new SubmissionNotFoundException(submissionId);
    }

    final Submission oldSubmission = oldSubmissionOptional.get();

    oldSubmission.accept();
    submissionRepository.save(oldSubmission);
  }

  @SuppressWarnings({"PMD.UnusedPrivateMethod"})
  @Scheduled(cron = "0 30 3 * * ?")
  private void deleteAllExpiredAcceptedSubmissions() {
    submissionRepository.deleteAllAcceptedSubmissionsOlderThan(
        timeFormatService.getKeepAcceptedSubmissionsExpirationDate());
  }
}
