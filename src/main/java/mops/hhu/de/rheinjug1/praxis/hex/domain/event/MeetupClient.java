package mops.hhu.de.rheinjug1.praxis.hex.domain.event;

import mops.hhu.de.rheinjug1.praxis.hex.domain.event.Event;

import java.util.List;

public interface MeetupClient {
    public List<Event> getAllEvents();
}
