package mops.hhu.de.rheinjug1.praxis.domain.submission.eventinfo;

import java.util.List;

public interface SubmissionEventInfoRepository {
    List<SubmissionEventInfo> getSubmissionInfoListByEmail(String email);
    List<SubmissionEventInfo> getAllEventsWithUserInfosByEmail(String email);
}
