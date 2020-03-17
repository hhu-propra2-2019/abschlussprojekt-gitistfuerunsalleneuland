package mops.hhu.de.rheinjug1.praxis.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mops.hhu.de.rheinjug1.praxis.database.entities.Submission;
import mops.hhu.de.rheinjug1.praxis.database.repositories.SubmissionRepository;
import mops.hhu.de.rheinjug1.praxis.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubmissionService {

  private final SubmissionRepository submissionRepository;

  @Autowired
  public SubmissionService(final SubmissionRepository submissionRepository) {
    this.submissionRepository = submissionRepository;
  }

  public List<Submission> getAllSubmissionsByUser(final Account account) {
    return submissionRepository.findAllByEmail(account.getEmail());
  }

  public Optional<Submission> getAcceptedSubmissionIfAuthorized(
      final Long submissionId, final Account account) {
    final Optional<Submission> acceptedSubmissionOptional =
        submissionRepository.findById(submissionId);
    if (acceptedSubmissionOptional.isEmpty()) {
      return acceptedSubmissionOptional;
    }

    final Submission submission = acceptedSubmissionOptional.get();
    if (Objects.equals(submission.getName(), account.getName())
        && Objects.equals(submission.getEmail(), account.getEmail())) {
      return acceptedSubmissionOptional;
    }

    return Optional.empty();
  }
}
