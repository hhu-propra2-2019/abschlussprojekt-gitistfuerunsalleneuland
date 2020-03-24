package mops.hhu.de.rheinjug1.praxis.services;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import mops.hhu.de.rheinjug1.praxis.models.SubmissionEventInfo;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TimeFormatService {

  @Value("${duration.upload.days}")
  private int uploadPeriodInDays;

  private static final LocalDateTime NOW = LocalDateTime.now();
  private static final String DATABASE_DATE_TIME_PATTERN = "HH:mm - dd.MM.yyyy";
  private static final String DATEPATTERN = "dd.MM.yyyy";

  public String format(final Duration duration) {
    final Calendar cal = Calendar.getInstance();
    cal.set(0, 0, 0, duration.toHoursPart(), duration.toMinutesPart());
    return String.format("%tH:%tM", cal, cal);
  }

  private ZonedDateTime toLocalEventTime(final ZonedDateTime utcTime, final ZoneId zoneId) {
    return utcTime.toOffsetDateTime().atZoneSameInstant(zoneId);
  }

  public String toLocalEventTimeString(final ZonedDateTime utcTime, final ZoneId zoneId) {
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATABASE_DATE_TIME_PATTERN);
    return toLocalEventTime(utcTime, zoneId).format(formatter);
  }

  public boolean isInTheFuture(final SubmissionEventInfo submissionEventInfo) {
    final LocalDateTime dateTime = getLocalDateTime(submissionEventInfo.getEventDateTime());
    return NOW.isBefore(dateTime);
  }

  public boolean isUploadPeriodExpired(final SubmissionEventInfo submissionEventInfo) {
    final LocalDateTime dateTime = getLocalDateTime(submissionEventInfo.getEventDateTime());
    return NOW.isAfter(dateTime.plusDays(uploadPeriodInDays));
  }

  public boolean isInUploadPeriod(final SubmissionEventInfo submissionEventInfo) {
    final LocalDateTime dateTime = getLocalDateTime(submissionEventInfo.getEventDateTime());

    return NOW.isBefore(dateTime.plusDays(uploadPeriodInDays)) && NOW.isAfter(dateTime);
  }

  public String getGermanDateString(final SubmissionEventInfo submissionEventInfo) {
    return getLocalDateTime(submissionEventInfo.getEventDateTime()).toString(DATEPATTERN);
  }

  public String getGermanTimeString(final Event event) {
    return getLocalDateTime(event.getZonedDateTime()).toString("HH:mm");
  }

  public String extractDate(final String zonedDateTime) {
    return getLocalDateTime(zonedDateTime).toString(DATEPATTERN);
  }

  public LocalDate getLocalDate(final String localDateString) {
    final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATEPATTERN);
    return LocalDate.parse(localDateString, dateTimeFormatter);
  }

  public LocalDateTime getLocalDateTime(final String dateTimeString) {
    final org.joda.time.format.DateTimeFormatter formatter =
        DateTimeFormat.forPattern(DATABASE_DATE_TIME_PATTERN);
    return formatter.parseLocalDateTime(dateTimeString);
  }
}
