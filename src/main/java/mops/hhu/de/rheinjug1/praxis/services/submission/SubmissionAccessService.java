package mops.hhu.de.rheinjug1.praxis.services.submission;

import java.util.Optional;
import mops.hhu.de.rheinjug1.praxis.database.entities.Submission;
import mops.hhu.de.rheinjug1.praxis.database.repositories.SubmissionRepository;
import mops.hhu.de.rheinjug1.praxis.exceptions.SubmissionNotFoundException;
import mops.hhu.de.rheinjug1.praxis.exceptions.UnauthorizedSubmissionAccessException;
import mops.hhu.de.rheinjug1.praxis.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubmissionAccessService {

  private final SubmissionRepository submissionRepository;

  @Autowired
  public SubmissionAccessService(final SubmissionRepository submissionRepository) {
    this.submissionRepository = submissionRepository;
  }

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
}
