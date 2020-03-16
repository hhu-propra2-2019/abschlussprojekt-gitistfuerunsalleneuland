package mops.hhu.de.rheinjug1.praxis.database.repositories;

import java.util.List;
import mops.hhu.de.rheinjug1.praxis.database.entities.AcceptedSubmission;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AcceptedSubmissionRepository extends CrudRepository<AcceptedSubmission, Long> {

  @Query("SELECT * FROM rheinjug1.accepted_submission subm WHERE subm.email = :email")
  List<AcceptedSubmission> findAll(@Param("email") final String email);
}
