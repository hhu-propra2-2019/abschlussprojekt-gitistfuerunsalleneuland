package mops.hhu.de.rheinjug1.praxis.services;

import java.time.Duration;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class FormatService {

  public String format(final Duration duration) {
    return "" + duration.toHoursPart() + ":" + duration.toMinutesPart();
  }

  public String zonedDateTimetoString(final ZonedDateTime zonedDateTime) {
    final Long offsetHours = offsetToHours(zonedDateTime.getOffset());
    final ZonedDateTime offsetDateTime = zonedDateTime.plusHours(offsetHours);
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm - dd.MM.yyyy");
    return offsetDateTime.format(formatter);
  }

  private Long offsetToHours(final ZoneOffset offset) {
    final String offsetString = offset.toString();
    final int index = offsetString.lastIndexOf(":");
    final String converted = offsetString.substring(0, index);
    return Long.parseLong(converted);
  }
}
