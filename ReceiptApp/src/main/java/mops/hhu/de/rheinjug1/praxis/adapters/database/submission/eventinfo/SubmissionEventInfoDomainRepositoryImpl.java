package mops.hhu.de.rheinjug1.praxis.adapters.database.submission.eventinfo;

import static java.util.stream.Collectors.*;

import java.util.List;
import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.domain.Account;
import mops.hhu.de.rheinjug1.praxis.domain.submission.eventinfo.SubmissionEventInfo;
import mops.hhu.de.rheinjug1.praxis.domain.submission.eventinfo.SubmissionEventInfoComparator;
import mops.hhu.de.rheinjug1.praxis.domain.submission.eventinfo.SubmissionEventInfoDomainRepository;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class SubmissionEventInfoDomainRepositoryImpl
    implements SubmissionEventInfoDomainRepository {
  private final SubmissionEventInfoDbRepository submissionEventInfoDbRepository;
  private final SubmissionEventInfoComparator comparator;

  @Override
  public List<SubmissionEventInfo> getAllSubmissionsWithInfosByUser(final Account account) {
    return submissionEventInfoDbRepository.getSubmissionInfoListByEmail(account.getEmail());
  }

  @Override
  public List<SubmissionEventInfo> getAllSubmissionsWithInfosByUserSorted(final Account account) {
    return getAllSubmissionsWithInfosByUser(account).stream().sorted(comparator).collect(toList());
  }

  @Override
  public List<SubmissionEventInfo> getAllEventsWithInfosByUser(final Account account) {
    return submissionEventInfoDbRepository.getAllEventsWithUserInfosByEmail(account.getEmail());
  }

  @Override
  public List<SubmissionEventInfo> getAllEventsWithInfosByUserSorted(final Account account) {
    return getAllEventsWithInfosByUser(account).stream().sorted(comparator).collect(toList());
  }

  @Override
  public List<SubmissionEventInfo> getAllSubmissionsWithInfos() {
    return submissionEventInfoDbRepository.getAllSubmissionsWithInfos();
  }

  @Override
  public List<SubmissionEventInfo> getAllSubmissionsWithInfosSorted() {
    return getAllSubmissionsWithInfos().stream().sorted(comparator).collect(toList());
  }
}
