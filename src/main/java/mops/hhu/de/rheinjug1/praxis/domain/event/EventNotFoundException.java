package mops.hhu.de.rheinjug1.praxis.domain.event;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "submission not found")
public class EventNotFoundException extends Exception {

  public EventNotFoundException(final Long meetupId) {
    super(String.format("Event with the id %d could not be found in database", meetupId));
  }
}
