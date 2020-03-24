package mops.hhu.de.rheinjug1.praxis.hex.domain.event;

import lombok.Builder;
import lombok.Value;
import mops.hhu.de.rheinjug1.praxis.hex.annotations.DB;
import mops.hhu.de.rheinjug1.praxis.hex.enums.MeetupType;
import org.springframework.data.annotation.Id;

@DB
@Value
@Builder
public class Event {
  @Id long id;
  String duration;
  String name;
  String status;
  String zonedDateTime;
  String link;
  String description;
  MeetupType meetupType;
}
