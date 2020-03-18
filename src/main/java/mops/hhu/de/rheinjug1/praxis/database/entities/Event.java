package mops.hhu.de.rheinjug1.praxis.database.entities;

import lombok.*;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import org.springframework.data.annotation.Id;

@Value
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
