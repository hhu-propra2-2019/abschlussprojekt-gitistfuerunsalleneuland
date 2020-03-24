package mops.hhu.de.rheinjug1.praxis.adapters.database.submission.eventinfo;

import mops.hhu.de.rheinjug1.praxis.adapters.database.DrivenAdapter;
import mops.hhu.de.rheinjug1.praxis.domain.Account;
import mops.hhu.de.rheinjug1.praxis.domain.submission.eventinfo.SubmissionEventInfo;
import mops.hhu.de.rheinjug1.praxis.domain.submission.eventinfo.SubmissionEventInfoComparator;
import mops.hhu.de.rheinjug1.praxis.domain.submission.eventinfo.SubmissionEventInfoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.util.stream.Collectors.*;

@Repository
public class SubmissionEventInfoRepositoryImpl extends DrivenAdapter<SubmissionEventInfo, SubmissionEventInfoDTO> implements SubmissionEventInfoRepository {
    private final SubmissionEventInfoBackendRepo submissionEventInfoBackendRepo;
    private final SubmissionEventInfoComparator comparator;

    public SubmissionEventInfoRepositoryImpl(SubmissionEventInfoBackendRepo submissionEventInfoBackendRepo, SubmissionEventInfoComparator comparator) {
        this.submissionEventInfoBackendRepo = submissionEventInfoBackendRepo;
        this.comparator = comparator;
        this.entity = SubmissionEventInfo.builder().build();
        this.dto = SubmissionEventInfoDTO.builder().build();
    }

    @Override
    public List<SubmissionEventInfo> getAllSubmissionsWithInfosByUser(final Account account) {
        return toEntity(submissionEventInfoBackendRepo.getSubmissionInfoListByEmail(account.getEmail()));
    }

    @Override
    public List<SubmissionEventInfo> getAllSubmissionsWithInfosByUserSorted(Account account) {
        return getAllSubmissionsWithInfosByUser(account).stream()
                .sorted(comparator)
                .collect(toList());
    }

    @Override
    public List<SubmissionEventInfo> getAllEventsWithInfosByUser(final Account account) {
        return toEntity(submissionEventInfoBackendRepo.getAllEventsWithUserInfosByEmail(account.getEmail()));
    }

    @Override
    public List<SubmissionEventInfo> getAllEventsWithInfosByUserSorted(Account account) {
        return getAllEventsWithInfosByUser(account).stream()
                .sorted(comparator)
                .collect(toList());
    }

    @Override
    public List<SubmissionEventInfo> getAllSubmissionsWithInfos() {
        return toEntity(submissionEventInfoBackendRepo.getAllSubmissionsWithInfos());
    }

    @Override
    public List<SubmissionEventInfo> getAllSubmissionsWithInfosSorted() {
        return getAllSubmissionsWithInfos().stream()
                .sorted(comparator)
                .collect(toList());
    }
}
