package mops.hhu.de.rheinjug1.praxis.domain.event;

import lombok.Builder;
import lombok.Value;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import org.springframework.data.annotation.Id;

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
