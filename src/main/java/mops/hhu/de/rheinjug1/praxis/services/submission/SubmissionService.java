package mops.hhu.de.rheinjug1.praxis.services.submission;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mops.hhu.de.rheinjug1.praxis.database.entities.Submission;
import mops.hhu.de.rheinjug1.praxis.database.repositories.SubmissionInfoRepository;
import mops.hhu.de.rheinjug1.praxis.database.repositories.SubmissionRepository;
import mops.hhu.de.rheinjug1.praxis.exceptions.SubmissionNotFoundException;
import mops.hhu.de.rheinjug1.praxis.exceptions.UnauthorizedSubmissionAccessException;
import mops.hhu.de.rheinjug1.praxis.models.Account;
import mops.hhu.de.rheinjug1.praxis.models.SubmissionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubmissionService {

  private final SubmissionRepository submissionRepository;
  private final SubmissionInfoRepository submissionInfoRepository;

  @Autowired
  public SubmissionService(
      final SubmissionRepository submissionRepository,
      final SubmissionInfoRepository submissionInfoRepository) {
    this.submissionRepository = submissionRepository;
    this.submissionInfoRepository = submissionInfoRepository;
  }

  public List<SubmissionInfo> getAllSubmissionsWithInfosByUser(final Account account) {
    return submissionInfoRepository.getSubmissionInfoListByEmail(account.getEmail());
  }

  public List<Submission> getAllSubmissions() {
    final ArrayList<Submission> arrayList = new ArrayList<>();
    submissionRepository.findAll().forEach(arrayList::add);
    return arrayList;
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

    if (!nameAndEmailAreEqual(submission, account)) {
      throw new UnauthorizedSubmissionAccessException(submissionId);
    }

    return submission;
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
