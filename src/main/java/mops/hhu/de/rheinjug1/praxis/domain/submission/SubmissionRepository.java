package mops.hhu.de.rheinjug1.praxis.domain.submission;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubmissionRepository {
    int countSubmissionByMeetupId(long id);
    Optional<Submission> findById(Long submissionId);
    void deleteAllAcceptedSubmissionsOlderThan(String expirationDate);
    void save(Submission submission);
    List<Submission> findAllByMeetupIdAndEmail(Long meetupId, String email);
}
