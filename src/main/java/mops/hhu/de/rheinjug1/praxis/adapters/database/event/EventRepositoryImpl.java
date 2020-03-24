package mops.hhu.de.rheinjug1.praxis.adapters.database.event;

import lombok.RequiredArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.adapters.database.DrivenAdapter;
import mops.hhu.de.rheinjug1.praxis.domain.event.Event;
import mops.hhu.de.rheinjug1.praxis.domain.event.EventRepository;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.springframework.beans.BeanUtils.copyProperties;

@RequiredArgsConstructor
public class EventRepositoryImpl extends DrivenAdapter<Event, EventDTO> implements EventRepository {
    private final EventBackendRepo eventBackendRepo;

    @Override
    public List<Event> findAll() {
        return eventBackendRepo.findAll()
                .stream()
                .map(this::toEvent)
                .collect(toList());
    }

    @Override
    public void save(Event event) {
        eventBackendRepo.save(toBackend(event));
    }

    @Override
    public Optional<Event> findById(Long meetUpId) {
        return toEvent(eventBackendRepo.findById(meetUpId));
    }

    private Event toEvent(EventDTO eventBackend) {
        Event event = Event.builder().build();
        copyProperties(eventBackend, event);
        return event;
    }

    private Optional<Event> toEvent(Optional<EventDTO> eventBackend) {
        if(eventBackend.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(toEvent(eventBackend.get()));
    }

    private EventDTO toBackend(Event event) {
        EventDTO backend = EventDTO.builder().build();
        copyProperties(event, eventD);
        return backend;
    }
}
