package mops.hhu.de.rheinjug1.praxis.database.entities;

import lombok.*;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import org.springframework.data.annotation.Id;

@Value
@Builder
public class Event {
  // brauchen wir hier @NonNull fuer alle Felder?
  // Vorher war das durch den All-Args-Konstruktor nicht verlangt; bei einem Builder ist aber
  // das Risiko durch vergessene Argumente groesser...
  @Id long id;
  String duration;
  String name;
  String status;
  String zonedDateTime;
  String link;
  String description;
  MeetupType meetupType;
}
