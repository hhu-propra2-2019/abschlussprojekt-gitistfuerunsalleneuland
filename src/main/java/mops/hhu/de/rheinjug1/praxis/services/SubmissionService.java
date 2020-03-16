package mops.hhu.de.rheinjug1.praxis.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mops.hhu.de.rheinjug1.praxis.database.entities.AcceptedSubmission;
import mops.hhu.de.rheinjug1.praxis.database.repositories.AcceptedSubmissionRepository;
import mops.hhu.de.rheinjug1.praxis.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubmissionService {

  private final AcceptedSubmissionRepository acceptedSubmissionRepository;

  @Autowired
  public SubmissionService(final AcceptedSubmissionRepository acceptedSubmissionRepository) {
    this.acceptedSubmissionRepository = acceptedSubmissionRepository;
  }

  public List<AcceptedSubmission> getAllAcceptedSubmissions(final Account account) {
    return acceptedSubmissionRepository.findAll(account.getEmail());
  }

  public Optional<AcceptedSubmission> getAcceptedSubmissionIfAuthorized(
      final Long submissionId, final Account account) {
    final Optional<AcceptedSubmission> acceptedSubmissionOptional =
        acceptedSubmissionRepository.findById(submissionId);
    if (acceptedSubmissionOptional.isEmpty()) {
      return acceptedSubmissionOptional;
    }

    final AcceptedSubmission acceptedSubmission = acceptedSubmissionOptional.get();
    if (Objects.equals(acceptedSubmission.getName(), account.getName())
        && Objects.equals(acceptedSubmission.getEmail(), account.getEmail())) {
      return acceptedSubmissionOptional;
    }

    return Optional.empty();
  }
}
