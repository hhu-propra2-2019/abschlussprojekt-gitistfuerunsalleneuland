package mops.hhu.de.rheinjug1.praxis.domain.event;

import mops.hhu.de.rheinjug1.praxis.adapters.meetup.EventMeetupDTO;

public interface EventFactory {
  Event createFromDTO(EventMeetupDTO dto);
}
