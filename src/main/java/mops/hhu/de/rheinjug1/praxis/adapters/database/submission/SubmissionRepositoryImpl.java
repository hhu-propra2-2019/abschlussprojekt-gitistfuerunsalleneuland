package mops.hhu.de.rheinjug1.praxis.adapters.database.submission;

import mops.hhu.de.rheinjug1.praxis.adapters.database.DrivenAdapter;
import mops.hhu.de.rheinjug1.praxis.domain.TimeFormatService;
import mops.hhu.de.rheinjug1.praxis.domain.submission.Submission;
import mops.hhu.de.rheinjug1.praxis.domain.submission.SubmissionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SubmissionRepositoryImpl extends DrivenAdapter<Submission, SubmissionDTO> implements SubmissionRepository {
    private final SubmissionBackendRepo submissionBackendRepo;

    public SubmissionRepositoryImpl(SubmissionBackendRepo submissionBackendRepo, TimeFormatService timeFormatService) {
        this.submissionBackendRepo = submissionBackendRepo;
        this.entity = Submission.builder()
                .timeFormatService(timeFormatService)
                .build();
        this.dto = new SubmissionDTO();
    }

    @Override
    public int countSubmissionByMeetupId(long id) {
        return submissionBackendRepo.countSubmissionByMeetupId(id);
    }

    @Override
    public Optional<Submission> findById(Long id) {
        return toEntity(submissionBackendRepo.findById(id));
    }

    @Override
    public void deleteAllAcceptedSubmissionsOlderThan(String expirationDate) {
        submissionBackendRepo.deleteAllAcceptedSubmissionsOlderThan(expirationDate);
    }

    @Override
    public void save(Submission submission) {
        submissionBackendRepo.save(toDTO(submission));
    }

    @Override
    public List<Submission> findAllByMeetupIdAndEmail(Long meetupId, String email) {
        return toEntity(submissionBackendRepo.findAllByMeetupIdAndEmail(meetupId, email));
    }
}
