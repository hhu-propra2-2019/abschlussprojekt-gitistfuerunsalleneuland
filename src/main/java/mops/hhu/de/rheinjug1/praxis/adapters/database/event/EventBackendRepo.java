package mops.hhu.de.rheinjug1.praxis.adapters.database.event;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface EventBackendRepo extends CrudRepository<EventDTO, Long> {

  @Override
  @Query("SELECT * FROM rheinjug1.event")
  List<EventDTO> findAll();
}
