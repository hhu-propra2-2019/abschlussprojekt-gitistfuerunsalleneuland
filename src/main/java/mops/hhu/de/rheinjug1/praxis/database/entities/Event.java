package mops.hhu.de.rheinjug1.praxis.database.entities;

import java.time.Duration;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Event {
  private final Duration duration;
  private final long id;
  private final String name;
  private final String status;
  private final ZonedDateTime zonedDateTime;
  private final Venue venue;
  private final String link;
  private final String description;

  public Event(
      final Duration duration,
      final long id,
      final String name,
      final String status,
      final ZonedDateTime zonedDateTime,
      final Venue venue,
      final String link,
      final String description) {
    this.duration = duration;
    this.id = id;
    this.name = name;
    this.status = status;
    this.zonedDateTime = zonedDateTime;
    this.venue = venue;
    this.link = link;
    this.description = description;
  }
}
