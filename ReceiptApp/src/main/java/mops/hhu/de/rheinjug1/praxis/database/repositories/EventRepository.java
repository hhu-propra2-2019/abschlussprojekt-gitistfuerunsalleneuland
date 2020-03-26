package mops.hhu.de.rheinjug1.praxis.database.repositories;

import java.util.List;
import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {

  @Override
  @Query("SELECT * FROM rheinjug1.event")
  List<Event> findAll();
}
