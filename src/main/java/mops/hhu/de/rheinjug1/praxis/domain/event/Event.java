package mops.hhu.de.rheinjug1.praxis.domain.event;

import lombok.Builder;
import lombok.Data;
import lombok.Value;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;

@Data
@Builder
public class Event {
  long id;
  String duration;
  String name;
  String status;
  String zonedDateTime;
  String link;
  String description;
  MeetupType meetupType;

}
