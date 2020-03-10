package mops;

import mops.hhu.de.rheinjug1.praxis.entities.AcceptedSubmission;
import mops.hhu.de.rheinjug1.praxis.services.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SuppressWarnings("PMD")
@SpringBootApplication
public class PraxisApplication {

  @Autowired private ReceiptService receiptService;

  public static void main(final String[] args) {
    SpringApplication.run(PraxisApplication.class, args);
  }

  @Bean
  ApplicationRunner init() {
    return args -> {
      final AcceptedSubmission submission = new AcceptedSubmission(0L, 0L);
      receiptService.createReceiptAndSaveSignatureInDatabase(submission, "testReceipt");
    };
  }
}
