package mops.hhu.de.rheinjug1.praxis.database.repositories;

import mops.hhu.de.rheinjug1.praxis.database.entities.SignatureRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignatureRepository extends CrudRepository<SignatureRecord, Long> {}
