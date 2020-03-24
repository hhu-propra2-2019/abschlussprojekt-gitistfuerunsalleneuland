package mops.hhu.de.rheinjug1.praxis.domain;

import mops.hhu.de.rheinjug1.praxis.domain.event.Event;
import mops.hhu.de.rheinjug1.praxis.domain.submission.eventinfo.SubmissionEventInfo;
import org.joda.time.LocalDateTime;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public interface TimeFormatService {
    String format(Duration duration);

    String toLocalEventTimeString(ZonedDateTime utcTime, ZoneId zoneId);

    boolean isInTheFuture(SubmissionEventInfo submissionEventInfo);

    boolean isUploadPeriodExpired(SubmissionEventInfo submissionEventInfo);

    boolean isInUploadPeriod(SubmissionEventInfo submissionEventInfo);

    String getGermanDateString(SubmissionEventInfo submissionEventInfo);

    String getGermanDateString(Event event);

    String getGermanTimeString(Event event);

    LocalDateTime getLocalDateTime(String dateTimeString);
}
