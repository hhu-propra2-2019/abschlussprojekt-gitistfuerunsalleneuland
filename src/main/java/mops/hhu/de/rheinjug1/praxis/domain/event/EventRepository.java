package mops.hhu.de.rheinjug1.praxis.domain.event;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository {
    List<Event> findAll();
    void save(Event event);
    Optional<Event> findById(Long meetUpId);
}
