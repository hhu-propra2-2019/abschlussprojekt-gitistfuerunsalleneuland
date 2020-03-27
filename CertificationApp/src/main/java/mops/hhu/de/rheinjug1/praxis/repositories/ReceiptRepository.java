package mops.hhu.de.rheinjug1.praxis.repositories;

import mops.hhu.de.rheinjug1.praxis.entities.ReceiptEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiptRepository extends CrudRepository<ReceiptEntity, Long> {

  @Query("SELECT * FROM rheinjug1.receipt WHERE receipt.id=:id")
  ReceiptEntity findReceiptEntityById(@Param("id") Long id);

  @Query("SELECT COUNT(signature) FROM receipts.receipt r WHERE r.meetup_type = :meetup_type;")
  int countSignatureByMeetupType(@Param("meetup_type") String meetupType);

  @Query("SELECT * FROM receipt WHERE receipt.signature = signature")
  List<ReceiptEntity> findReceiptEntityBySignature(@Param("signature") String signature);
}

