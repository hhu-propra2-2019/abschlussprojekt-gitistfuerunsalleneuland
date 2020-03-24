package mops.hhu.de.rheinjug1.praxis.domain.submission.eventinfo;

import mops.hhu.de.rheinjug1.praxis.domain.Account;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionEventInfoRepository {
  List<SubmissionEventInfo> getAllSubmissionsWithInfosByUser(final Account account);
  List<SubmissionEventInfo> getAllSubmissionsWithInfosByUserSorted(final Account account);
  List<SubmissionEventInfo> getAllEventsWithInfosByUser(final Account account);
  List<SubmissionEventInfo> getAllEventsWithInfosByUserSorted(final Account account);
  List<SubmissionEventInfo> getAllSubmissionsWithInfos();
  List<SubmissionEventInfo> getAllSubmissionsWithInfosSorted();
}
