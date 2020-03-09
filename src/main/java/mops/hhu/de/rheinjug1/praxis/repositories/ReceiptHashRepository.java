package mops.hhu.de.rheinjug1.praxis.repositories;

import mops.hhu.de.rheinjug1.praxis.entities.ReceiptHash;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptHashRepository extends CrudRepository<ReceiptHash, Long> {

  @Query("SELECT * FROM rheinjug1.receipt WHERE receipt.id=:id")
  ReceiptHash findReceiptById(@Param("id") Long id);
}
