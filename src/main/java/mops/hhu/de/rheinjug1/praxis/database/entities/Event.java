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
    this.zonedDateTime = zonedDateTimetoString(zonedDateTime);
    this.link = link;
    this.description = description;
  }

  private String format(final Duration duration) {
    return "" + duration.toHoursPart() + ":" + duration.toMinutesPart();
  }

  private String zonedDateTimetoString(final ZonedDateTime zonedDateTime) {
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
