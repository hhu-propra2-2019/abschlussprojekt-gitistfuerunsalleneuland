package mops.hhu.de.rheinjug1.praxis.clients.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings({"PMD.FieldNamingConventions", "PMD.TooManyFields"})
@Getter
@Setter
@NoArgsConstructor
public class Event {
  private long created;
  private long duration;
  private Fee fee;
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
  private Venue venue;
  private Group group;
  private String link;
  private String description;
  private String how_to_find_us;
  private String visibility;
  private boolean member_pay_fee;

  @Override
  public String toString() {
    return "Event{"
        + "created="
        + created
        + ", duration="
        + duration
        + ", fee="
        + fee
        + ", id='"
        + id
        + '\''
        + ", name='"
        + name
        + '\''
        + ", date_in_series_pattern="
        + date_in_series_pattern
        + ", status='"
        + status
        + '\''
        + ", time="
        + time
        + ", local_date='"
        + local_date
        + '\''
        + ", local_time='"
        + local_time
        + '\''
        + ", updated="
        + updated
        + ", utc_offset="
        + utc_offset
        + ", waitlist_count="
        + waitlist_count
        + ", yes_rsvp_count="
        + yes_rsvp_count
        + ", venue="
        + venue
        + ", group="
        + group
        + ", link='"
        + link
        + '\''
        + ", description='"
        + description
        + '\''
        + ", how_to_find_us='"
        + how_to_find_us
        + '\''
        + ", visibility='"
        + visibility
        + '\''
        + ", member_pay_fee="
        + member_pay_fee
        + '}';
  }
}
