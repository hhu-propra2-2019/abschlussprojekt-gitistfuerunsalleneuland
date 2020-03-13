package mops.hhu.de.rheinjug1.praxis.exceptions;

public class EventNotFoundException extends Exception {

  public EventNotFoundException(final Long meetupId) {
    super(String.format("Event with the id %d could not be found in database", meetupId));
  }
}
