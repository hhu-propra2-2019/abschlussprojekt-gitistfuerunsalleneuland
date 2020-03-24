package mops.hhu.de.rheinjug1.praxis.domain.submission.eventinfo;

import java.util.Comparator;

public interface SubmissionEventInfoComparator extends Comparator<SubmissionEventInfo> {
    int compare(SubmissionEventInfo s1, SubmissionEventInfo s2);
}
