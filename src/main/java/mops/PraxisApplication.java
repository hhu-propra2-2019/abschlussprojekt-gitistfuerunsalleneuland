package mops;

import mops.hhu.de.rheinjug1.praxis.database.entities.AcceptedSubmission;
import mops.hhu.de.rheinjug1.praxis.database.repositories.AcceptedSubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SuppressWarnings("PMD")
@SpringBootApplication
@EnableScheduling
public class PraxisApplication {

  @Autowired AcceptedSubmissionRepository acceptedSubmissionRepository;

  public static void main(final String[] args) {
    SpringApplication.run(PraxisApplication.class, args);
  }

  @Bean
  ApplicationRunner init() {
    return args -> {
      final AcceptedSubmission acceptedSubmission =
          new AcceptedSubmission(256264912L, "studentin@student.in", "studentin", "");
      acceptedSubmissionRepository.save(acceptedSubmission);
    };
  }
}
