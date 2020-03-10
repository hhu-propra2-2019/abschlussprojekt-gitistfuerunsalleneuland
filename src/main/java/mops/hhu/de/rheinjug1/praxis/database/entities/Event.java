package mops.hhu.de.rheinjug1.praxis.database.entities;

import java.time.*;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@ToString
@Getter
public class Event {

  @Id private long id;
  private final String duration;
  private final String name;
  private final String status;
  private final String zonedDateTime;
  private final String link;
  private final String description;

  public Event(
      final Duration duration,
      final long id,
      final String name,
      final String status,
      final ZonedDateTime zonedDateTime,
      final String link,
      final String description) {
    this.duration = format(duration);
    this.id = id;
    this.name = name;
    this.status = status;
    this.zonedDateTime = toString(zonedDateTime);
    this.link = link;
    this.description = description;
    toString(zonedDateTime);
  }

  private String format(final Duration duration) {
    return "" + duration.toHoursPart() + ":" + duration.toMinutesPart();
  }

  private String toString(final ZonedDateTime zonedDateTime) {
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm - dd.MM.yyyy");
    final ZonedDateTime withOffset =
        zonedDateTime.toOffsetDateTime().atZoneSameInstant(ZoneId.of("Europe/Berlin"));
    return withOffset.format(formatter);
  }
}
