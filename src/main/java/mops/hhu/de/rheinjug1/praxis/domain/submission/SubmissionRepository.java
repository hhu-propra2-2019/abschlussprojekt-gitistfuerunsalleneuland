package mops.hhu.de.rheinjug1.praxis.domain.submission;

import java.util.List;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionRepository extends CrudRepository<Submission, Long> {

  @Query("SELECT * FROM rheinjug1.submission subm WHERE subm.email = :email")
  List<Submission> findAllByEmail(@Param("email") final String email);

  @Query("SELECT COUNT(*) FROM rheinjug1.submission WHERE submission.meetup_id = :id")
  int countSubmissionByMeetupId(@Param("id") Long id);

  @Query(
      "SELECT * FROM rheinjug1.submission subm WHERE subm.email = :email AND subm.meetup_id = :meetupId")
  List<Submission> findAllByMeetupIdAndEmail(final Long meetupId, final String email);
}
