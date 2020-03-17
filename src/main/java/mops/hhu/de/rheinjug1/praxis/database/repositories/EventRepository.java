package mops.hhu.de.rheinjug1.praxis.database.repositories;

import java.util.List;
import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends CrudRepository<Event, Long> {

  @Override
  @Query("SELECT * FROM rheinjug1.event")
  List<Event> findAll();

  @Query("SELECT COUNT(*) FROM rheinjug1.submission WHERE meetup_id = :id")
  int submissionCountByMeetupId(
      @Param("id") Long id); // Todo: Write this method in SubmissionRepository when implemented
}
