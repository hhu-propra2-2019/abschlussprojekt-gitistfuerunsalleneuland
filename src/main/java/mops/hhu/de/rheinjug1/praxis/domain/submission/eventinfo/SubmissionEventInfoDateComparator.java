package mops.hhu.de.rheinjug1.praxis.domain.submission.eventinfo;

import mops.hhu.de.rheinjug1.praxis.domain.TimeFormatService;
import mops.hhu.de.rheinjug1.praxis.domain.submission.eventinfo.SubmissionEventInfo;
import mops.hhu.de.rheinjug1.praxis.domain.submission.eventinfo.SubmissionEventInfoComparator;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class SubmissionEventInfoDateComparator implements SubmissionEventInfoComparator {

  private final TimeFormatService timeFormatService;

  public SubmissionEventInfoDateComparator(TimeFormatService timeFormatService) {
    this.timeFormatService = timeFormatService;
  }

  @Override
  public int compare(final SubmissionEventInfo s1, final SubmissionEventInfo s2) {
    final LocalDateTime dateTime1 = timeFormatService.getLocalDateTime(s1.getEventDateTime());
    final LocalDateTime dateTime2 = timeFormatService.getLocalDateTime(s2.getEventDateTime());
    return dateTime2.compareTo(dateTime1);
  }
}
