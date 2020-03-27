package mops.hhu.de.rheinjug1.praxis.domain.receipt;

import java.util.List;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptRepository extends CrudRepository<ReceiptEntity, Long> {

  @Query("SELECT * FROM receipt WHERE receipt.id=:id")
  ReceiptEntity findReceiptEntityById(@Param("id") Long id);

  @Query("SELECT * FROM receipt WHERE receipt.signature=:signature")
  List<ReceiptEntity> findReceiptEntityBySignature(@Param("signature") String signature);
}
