package mops.hhu.de.rheinjug1.praxis.adapters.meetup;

import static mops.hhu.de.rheinjug1.praxis.enums.MeetupType.*;

import java.time.*;

import lombok.*;
import mops.hhu.de.rheinjug1.praxis.adapters.meetup.dto.FeeResponseDTO;
import mops.hhu.de.rheinjug1.praxis.adapters.meetup.dto.GroupResponseDTO;
import mops.hhu.de.rheinjug1.praxis.adapters.meetup.dto.VenueResponseDTO;
import mops.hhu.de.rheinjug1.praxis.annotations.DTO;
import mops.hhu.de.rheinjug1.praxis.domain.event.Event;
import mops.hhu.de.rheinjug1.praxis.domain.TimeFormatService;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings({
  "PMD.FieldNamingConventions",
  "PMD.TooManyFields",
  "PMD.UseLocaleWithCaseConversions"
})
@DTO
@ToString
@Setter
@Getter(AccessLevel.PACKAGE)
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
}
