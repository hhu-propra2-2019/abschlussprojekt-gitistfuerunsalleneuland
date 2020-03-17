package mops;

import mops.hhu.de.rheinjug1.praxis.database.entities.Submission;
import mops.hhu.de.rheinjug1.praxis.database.repositories.SubmissionRepository;
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

  @Autowired SubmissionRepository submissionRepository;

  public static void main(final String[] args) {
    SpringApplication.run(PraxisApplication.class, args);
  }

  @Bean
  ApplicationRunner init() {
    return args -> {
      final Submission submission =
          new Submission(256264912L, "ottolin@outlook.de", "otlin100", "");
      final Submission acceptedSubmission =
          new Submission(247416797L, "ottolin@outlook.de", "otlin100", "", true);
      Thread.sleep(5000);
      submissionRepository.save(submission);
      submissionRepository.save(acceptedSubmission);
    };
  }
}
