package mops.hhu.de.rheinjug1.praxis.services;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import org.springframework.stereotype.Service;

@Service
public class TimeFormatService {

  public String format(final Duration duration) {
    final Calendar cal = Calendar.getInstance();
    cal.set(0, 0, 0, duration.toHoursPart(), duration.toMinutesPart());
    return String.format("%tH:%tM", cal, cal);
  }

  private ZonedDateTime toLocalEventTime(final ZonedDateTime utcTime, final ZoneId zoneId) {
    return utcTime.toOffsetDateTime().atZoneSameInstant(zoneId);
  }

  public String toLocalEventTimeString(final ZonedDateTime utcTime, final ZoneId zoneId) {
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm - dd.MM.yyyy");
    return toLocalEventTime(utcTime, zoneId).format(formatter);
  }
}
