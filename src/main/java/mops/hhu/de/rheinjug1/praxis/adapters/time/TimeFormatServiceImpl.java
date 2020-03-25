package mops.hhu.de.rheinjug1.praxis.adapters.time;

import static org.joda.time.LocalDateTime.*;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import mops.hhu.de.rheinjug1.praxis.domain.TimeFormatService;
import mops.hhu.de.rheinjug1.praxis.domain.event.Event;
import mops.hhu.de.rheinjug1.praxis.domain.submission.eventinfo.SubmissionEventInfo;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TimeFormatServiceImpl implements TimeFormatService {

  @Value("${duration.upload.days}")
  private int uploadPeriodInDays;

  @Value("${duration.keep-accepted-submissions.days}")
  private int keepDurationInDays;

  public static final String DATABASE_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

  @Override
  public String getDatabaseDateTimePattern() {
    return DATABASE_DATE_TIME_PATTERN;
  }

  @Override
  public String format(final Duration duration) {
    final Calendar cal = Calendar.getInstance();
    cal.set(0, 0, 0, duration.toHoursPart(), duration.toMinutesPart());
    return String.format("%tH:%tM", cal, cal);
  }

  private ZonedDateTime toLocalEventTime(final ZonedDateTime utcTime, final ZoneId zoneId) {
    return utcTime.toOffsetDateTime().atZoneSameInstant(zoneId);
  }

  @Override
  public String toLocalEventTimeString(final ZonedDateTime utcTime, final ZoneId zoneId) {
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATABASE_DATE_TIME_PATTERN);
    return toLocalEventTime(utcTime, zoneId).format(formatter);
  }

  @Override
  public boolean isInTheFuture(final SubmissionEventInfo submissionEventInfo) {
    final LocalDateTime dateTime = getLocalDateTime(submissionEventInfo.getEventDateTime());
    return now().isBefore(dateTime);
  }

  @Override
  public boolean isUploadPeriodExpired(final SubmissionEventInfo submissionEventInfo) {
    final LocalDateTime dateTime = getLocalDateTime(submissionEventInfo.getEventDateTime());
    return now().isAfter(dateTime.plusDays(uploadPeriodInDays));
  }

  @Override
  public String getKeepAcceptedSubmissionsExpirationDate() {
    return now().minusDays(keepDurationInDays).toString(DATABASE_DATE_TIME_PATTERN);
  }

  @Override
  public boolean isInUploadPeriod(final SubmissionEventInfo submissionEventInfo) {
    final LocalDateTime dateTime = getLocalDateTime(submissionEventInfo.getEventDateTime());

    return now().isBefore(dateTime.plusDays(uploadPeriodInDays)) && now().isAfter(dateTime);
  }

  @Override
  public String getGermanDateString(final SubmissionEventInfo submissionEventInfo) {
    return getLocalDateTime(submissionEventInfo.getEventDateTime()).toString("dd.MM.yyyy");
  }

  @Override
  public String getGermanDateString(final Event event) {
    return getLocalDateTime(event.getZonedDateTime()).toString("dd.MM.yyyy");
  }

  @Override
  public String getGermanDateTimeString(final Event event) {
    return getLocalDateTime(event.getZonedDateTime()).toString("HH:mm - dd.MM.yyyy");
  }

  @Override
  public String getGermanTimeString(final Event event) {
    return getLocalDateTime(event.getZonedDateTime()).toString("HH:mm");
  }

  @Override
  public LocalDateTime getLocalDateTime(final String dateTimeString) {
    final org.joda.time.format.DateTimeFormatter formatter =
        DateTimeFormat.forPattern(DATABASE_DATE_TIME_PATTERN);
    return formatter.parseLocalDateTime(dateTimeString);
  }
}
