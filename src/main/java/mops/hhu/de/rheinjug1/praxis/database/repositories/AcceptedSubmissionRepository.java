package mops.hhu.de.rheinjug1.praxis.database.repositories;

import mops.hhu.de.rheinjug1.praxis.database.entities.AcceptedSubmission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcceptedSubmissionRepository extends CrudRepository<AcceptedSubmission, Long> {}
