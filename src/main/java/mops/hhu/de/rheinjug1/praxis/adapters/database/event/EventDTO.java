package mops.hhu.de.rheinjug1.praxis.adapters.database.event;

import lombok.Builder;
import lombok.Value;
import mops.hhu.de.rheinjug1.praxis.annotations.DB;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

@DB
@Value
@Builder
public class EventDTO {
  @Id long id;
  String duration;
  String name;
  String status;
  String zonedDateTime;
  String link;
  String description;
  MeetupType meetupType;

}
