package mops.hhu.de.rheinjug1.praxis.adapters.web.thymeleaf;

import static org.springframework.beans.BeanUtils.copyProperties;

import lombok.Data;
import mops.hhu.de.rheinjug1.praxis.domain.TimeFormatService;
import mops.hhu.de.rheinjug1.praxis.domain.event.Event;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;

@Data
public class TemplateEvent {
  long id;
  String duration;
  String name;
  String status;
  String zonedDateTime;
  String link;
  String description;
  MeetupType meetupType;
  String germanDateTime;

  public TemplateEvent(final Event event, final TimeFormatService timeFormatService) {
    copyProperties(event, this);
    this.germanDateTime = timeFormatService.getGermanDateTimeString(event);
  }
}
