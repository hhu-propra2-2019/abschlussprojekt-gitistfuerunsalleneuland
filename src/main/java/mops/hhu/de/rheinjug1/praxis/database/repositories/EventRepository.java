package mops.hhu.de.rheinjug1.praxis.database.repositories;

import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {}
