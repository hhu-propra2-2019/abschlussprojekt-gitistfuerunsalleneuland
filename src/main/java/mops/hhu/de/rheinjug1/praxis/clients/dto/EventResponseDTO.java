package mops.hhu.de.rheinjug1.praxis.clients.dto;

import java.time.*;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mops.hhu.de.rheinjug1.praxis.database.entities.Event;

@SuppressWarnings({"PMD.FieldNamingConventions", "PMD.TooManyFields"})
@ToString
@Setter
@NoArgsConstructor
public class EventResponseDTO {
  private long created;
  private long duration;
  private FeeResponseDTO fee;
  private String id;
  private String name;
  private boolean date_in_series_pattern;
  private String status;
  private long time;
  private String local_date;
  private String local_time;
  private long updated;
  private long utc_offset;
  private long waitlist_count;
  private long yes_rsvp_count;
  private VenueResponseDTO venue;
  private GroupResponseDTO group;
  private String link;
  private String description;
  private String how_to_find_us;
  private String visibility;
  private boolean member_pay_fee;

  public Event toEvent() {
    final Duration duration = Duration.ofMillis(this.duration);
    final long id = Long.parseLong(this.id);
    final Duration timeAsDuration = Duration.ofMillis(this.time);
    final LocalDateTime dateTime =
        LocalDateTime.ofEpochSecond(timeAsDuration.toSeconds(), 0, ZoneOffset.UTC);
    final ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, ZoneId.of("UTC"));
    return new Event(duration, id, name, status, zonedDateTime, link, description);
  };
}
