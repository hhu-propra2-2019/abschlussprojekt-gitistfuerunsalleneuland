package mops.hhu.de.rheinjug1.praxis.domain.event;

import mops.hhu.de.rheinjug1.praxis.adapters.meetup.EventResponseDTO;

public interface EventFactory {
    public Event createFromDTO(EventResponseDTO dto);
}
