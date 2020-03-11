package mops.hhu.de.rheinjug1.praxis.services;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class FormatService {

  public String format(final Duration duration) {
    return "" + duration.toHoursPart() + ":" + duration.toMinutesPart();
  }
  
  private ZonedDateTime toBerlinEuropeZone(final ZonedDateTime utcTime) {
    return utcTime.toOffsetDateTime().atZoneSameInstant(ZoneId.of("Europe/Berlin"));
  }

  public String toGermanTimeString(final ZonedDateTime utcTime) {
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm - dd.MM.yyyy");
    return toBerlinEuropeZone(utcTime).format(formatter);
  }
}
