package mops.hhu.de.rheinjug1.praxis.domain.submission;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.domain.Account;
import mops.hhu.de.rheinjug1.praxis.domain.submission.eventinfo.SubmissionEventInfo;
import mops.hhu.de.rheinjug1.praxis.domain.submission.eventinfo.SubmissionEventInfoRepository;
import mops.hhu.de.rheinjug1.praxis.domain.submission.exception.SubmissionNotFoundException;
import mops.hhu.de.rheinjug1.praxis.domain.submission.exception.UnauthorizedSubmissionAccessException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubmissionService {

  private final SubmissionRepository submissionRepository;
  private final SubmissionEventInfoRepository submissionEventInfoRepository;

  public List<SubmissionEventInfo> getAllSubmissionsWithInfosByUser(final Account account) {
    return submissionEventInfoRepository.getSubmissionInfoListByEmail(account.getEmail());
  }

  public List<Submission> getAllSubmissions() {
    final ArrayList<Submission> arrayList = new ArrayList<>();
    submissionRepository.findAll().forEach(arrayList::add);
    return arrayList;
  }

  public Optional<Submission> getAcceptedSubmissionIfAuthorized(
      final Long submissionId, final Account account)
      throws SubmissionNotFoundException, UnauthorizedSubmissionAccessException {

    final Optional<Submission> acceptedSubmissionOptional =
        submissionRepository.findById(submissionId);

    if (acceptedSubmissionOptional.isEmpty()) {
      throw new SubmissionNotFoundException(submissionId);
    }

    final Submission submission = acceptedSubmissionOptional.get();

    if (!nameAndEmailAreEqual(submission, account)) {
      throw new UnauthorizedSubmissionAccessException(submissionId);
    }

    return Optional.of(submission);
  }

  private boolean nameAndEmailAreEqual(final Submission submission, final Account account) {
    return Objects.equals(submission.getName(), account.getName())
        && Objects.equals(submission.getEmail(), account.getEmail());
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
