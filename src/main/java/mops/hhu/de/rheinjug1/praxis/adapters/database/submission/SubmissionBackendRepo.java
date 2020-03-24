package mops.hhu.de.rheinjug1.praxis.adapters.database.submission;

import java.util.List;

import mops.hhu.de.rheinjug1.praxis.domain.submission.Submission;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionBackendRepo extends CrudRepository<SubmissionDTO, Long> {

  @Query("SELECT * FROM rheinjug1.submission subm WHERE subm.email = :email")
  List<SubmissionDTO> findAllByEmail(@Param("email") final String email);

  @Query("SELECT COUNT(*) FROM rheinjug1.submission WHERE submission.meetup_id = :id")
  int countSubmissionByMeetupId(@Param("id") Long id);

  @Query(
      "SELECT * FROM rheinjug1.submission subm WHERE subm.email = :email AND subm.meetup_id = :meetupId")
  List<SubmissionDTO> findAllByMeetupIdAndEmail(final Long meetupId, final String email);

  @Modifying
  @Query(
      "DELETE FROM rheinjug1.submission WHERE accepted = 1 AND DATEDIFF(acceptance_date_time, :earliestPossibleDateTime) < 0")
  void deleteAllAcceptedSubmissionsOlderThan(final String earliestPossibleDateTime);
}
