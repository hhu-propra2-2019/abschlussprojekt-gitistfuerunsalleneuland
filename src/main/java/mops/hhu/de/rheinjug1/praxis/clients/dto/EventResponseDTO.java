package mops.hhu.de.rheinjug1.praxis.clients.dto;

import static mops.hhu.de.rheinjug1.praxis.enums.MeetupType.*;

import java.time.*;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.services.FormatService;

@SuppressWarnings({
  "PMD.FieldNamingConventions",
  "PMD.TooManyFields",
  "PMD.UseLocaleWithCaseConversions"
})
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

  public Event toEvent() { // translate the DTO to the Event Object that is saved in the database
    final FormatService formatter =
        new FormatService(); // Formatter to format Java Duration and Java zonedDateTime
    final Duration duration = Duration.ofMillis(this.duration);
    final long id = Long.parseLong(this.id);
    final Duration timeAsDuration = Duration.ofMillis(this.time);
    final LocalDateTime dateTime =
        LocalDateTime.ofEpochSecond(timeAsDuration.toSeconds(), 0, ZoneOffset.UTC);
    final ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, ZoneId.of("UTC"));
    final String formattedDuration = formatter.format(duration);
    final String formattedZonedDateTime =
        formatter.toLocalEventTimeString(zonedDateTime, group.getZoneId());
    final String compareString = name.toLowerCase();
    final MeetupType meetupType = compareString.contains("entwickelbar") ? ENTWICKELBAR : RHEINJUG;

    return new Event(
        formattedDuration, id, name, status, formattedZonedDateTime, link, description, meetupType);
  }
}
