package mops.hhu.de.rheinjug1.praxis.domain.event;

import mops.hhu.de.rheinjug1.praxis.adapters.meetup.EventResponseDTO;

public interface EventFactory {
  Event createFromDTO(EventResponseDTO dto);
}
