package mops.hhu.de.rheinjug1.praxis.database.repositories;

import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepository extends CrudRepository<Event,Long> {

    List<Event> findAll();

}
