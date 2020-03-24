package mops.hhu.de.rheinjug1.praxis.domain.event;

import java.util.List;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {

  @Override
  @Query("SELECT * FROM rheinjug1.event")
  List<Event> findAll();
}
