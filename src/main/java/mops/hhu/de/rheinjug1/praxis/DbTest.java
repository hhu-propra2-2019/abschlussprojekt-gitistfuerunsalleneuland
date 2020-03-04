package mops.hhu.de.rheinjug1.praxis;

import mops.hhu.de.rheinjug1.praxis.entities.Receipt;
import mops.hhu.de.rheinjug1.praxis.repositories.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DbTest {

    ReceiptRepository repo;

    public DbTest(ReceiptRepository repo) {
        this.repo = repo;
    }

    public Receipt findReceiptById(Long id){
        return repo.findReceiptById(id);
    }

    public void saveAll(List<Receipt> all){
        repo.saveAll(all);
    }

    public void save(Receipt r){
        repo.save(r);
    }
}
