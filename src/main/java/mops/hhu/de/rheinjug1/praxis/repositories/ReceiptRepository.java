package mops.hhu.de.rheinjug1.praxis.repositories;


import mops.hhu.de.rheinjug1.praxis.entities.Receipt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptRepository extends CrudRepository<Receipt,Long> {

    public Receipt findReceiptByById(Long id);
}
