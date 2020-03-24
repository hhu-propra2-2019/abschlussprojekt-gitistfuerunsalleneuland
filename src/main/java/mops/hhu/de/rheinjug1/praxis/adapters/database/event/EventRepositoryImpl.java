package mops.hhu.de.rheinjug1.praxis.adapters.database.event;

import mops.hhu.de.rheinjug1.praxis.adapters.database.DrivenAdapter;
import mops.hhu.de.rheinjug1.praxis.domain.event.Event;
import mops.hhu.de.rheinjug1.praxis.domain.event.EventRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class EventRepositoryImpl extends DrivenAdapter<Event, EventDTO> implements EventRepository {
    private final EventBackendRepo eventBackendRepo;

    public EventRepositoryImpl(final EventBackendRepo eventBackendRepo) {
        this.eventBackendRepo = eventBackendRepo;
        this.entity = Event.builder().build();
        this.dto = EventDTO.builder().build();
    }

    @Override
    public List<Event> findAll() {
        return toEntity(eventBackendRepo.findAll());
    }

    @Override
    public void save(Event event) {
        eventBackendRepo.save(toDTO(event));
    }

    @Override
    public Optional<Event> findById(Long meetUpId) {
        return toEntity(eventBackendRepo.findById(meetUpId));
    }
}
