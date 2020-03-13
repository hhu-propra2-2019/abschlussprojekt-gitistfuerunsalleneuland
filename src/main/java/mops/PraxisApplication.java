package mops;

import com.jlefebure.spring.boot.minio.MinioService;
import org.apache.http.entity.ContentType;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;

@SuppressWarnings("PMD")
@SpringBootApplication
@EnableScheduling
public class PraxisApplication {

  public static void main(final String[] args) {
    SpringApplication.run(PraxisApplication.class, args);
  }

  @Bean
  ApplicationRunner init(MinioService minioService) {
    return args -> {
      File newFile = new File("src/test/resources/test.txt");
      InputStream fileStream = new FileInputStream(newFile);

      System.out.println(minioService);

      minioService.upload(Path.of("test.txt"), fileStream, ContentType.TEXT_PLAIN);
      System.out.println("Hello World!");
    };
  }
}
