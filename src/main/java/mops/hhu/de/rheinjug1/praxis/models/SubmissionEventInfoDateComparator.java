package mops.hhu.de.rheinjug1.praxis.models;

import java.util.Comparator;
import mops.hhu.de.rheinjug1.praxis.services.TimeFormatService;
import org.joda.time.LocalDateTime;

public class SubmissionEventInfoDateComparator implements Comparator<SubmissionEventInfo> {

  private final TimeFormatService timeFormatService = new TimeFormatService();

  @Override
  public int compare(final SubmissionEventInfo s1, final SubmissionEventInfo s2) {
    final LocalDateTime dateTime1 = timeFormatService.getLocalDateTime(s1.getEventDateTime());
    final LocalDateTime dateTime2 = timeFormatService.getLocalDateTime(s2.getEventDateTime());
    return dateTime2.compareTo(dateTime1);
  }
}
