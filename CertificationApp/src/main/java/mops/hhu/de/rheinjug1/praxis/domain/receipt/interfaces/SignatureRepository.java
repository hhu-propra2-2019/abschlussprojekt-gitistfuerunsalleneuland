package mops.hhu.de.rheinjug1.praxis.domain.receipt.interfaces;

import java.util.List;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.entities.SignatureRecord;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SignatureRepository extends CrudRepository<SignatureRecord, Long> {

  @Query("SELECT * FROM receipt WHERE receipt.id=:id")
  SignatureRecord findReceiptEntityById(@Param("id") Long id);

  @Query("SELECT * FROM receipt WHERE receipt.signature=:signature")
  List<SignatureRecord> findReceiptEntityBySignature(@Param("signature") byte[] signature);
}
