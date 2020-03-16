package mops;

import mops.hhu.de.rheinjug1.praxis.services.UploadService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SuppressWarnings("PMD")
@SpringBootApplication
@EnableScheduling
public class PraxisApplication {

  public static void main(final String[] args) {
    SpringApplication.run(PraxisApplication.class, args);
  }

  @Bean
  ApplicationRunner init(UploadService uploadService) {
    return args -> {
      uploadService.uploadFile("testfile.adoc", "README.adoc");
    };
  }
}
