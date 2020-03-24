package mops.hhu.de.rheinjug1.praxis.adapters.database.receipt;

import mops.hhu.de.rheinjug1.praxis.domain.receipt.SignatureRecord;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SignatureBackendRepo extends CrudRepository<SignatureDTO, Long> {

  @Query(
      "SELECT COUNT(DISTINCT s.meetup_id) FROM rheinjug1.event e, rheinjug1.signature_record s WHERE s.meetup_id = e.id AND e.meetup_type = :meetup_type;")
  int countSignatureByMeetupType(@Param("meetup_type") String meetupType);
}
