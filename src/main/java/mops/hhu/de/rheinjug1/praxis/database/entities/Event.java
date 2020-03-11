package mops.hhu.de.rheinjug1.praxis.database.entities;

import lombok.*;
import org.springframework.data.annotation.Id;

@ToString
@Getter
@EqualsAndHashCode
public class Event {

  @Id private long id;
  private final String duration;
  private final String name;
  private final String status;
  private final String zonedDateTime;
  private final String link;
  private final String description;

  public Event(
      final String duration,
      final long id,
      final String name,
      final String status,
      final String zonedDateTime,
      final String link,
      final String description) {
    this.duration = duration;
    this.id = id;
    this.name = name;
    this.status = status;
    this.zonedDateTime = zonedDateTime;
    this.link = link;
    this.description = description;
  }
}
