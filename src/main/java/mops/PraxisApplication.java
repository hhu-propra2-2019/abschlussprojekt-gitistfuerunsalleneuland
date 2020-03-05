package mops;

import mops.hhu.de.rheinjug1.praxis.entities.Receipt;
import mops.hhu.de.rheinjug1.praxis.repositories.ReceiptRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SuppressWarnings("PMD")
@SpringBootApplication
public class PraxisApplication {

    public static void main(String[] args) {
        SpringApplication.run(PraxisApplication.class, args);
    }

}
