package mops;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;

import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jlefebure.spring.boot.minio.MinioService;

@SuppressWarnings("PMD")
@SpringBootApplication
public class PraxisApplication implements ApplicationRunner {

  public static void main(String[] args) {
    SpringApplication.run(PraxisApplication.class, args);
  }
  
   @Autowired
	MinioService minioService;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		File newFile = new File("src/test/resources/test.txt");
		InputStream fileStream = new FileInputStream(newFile);
		
		System.out.println(minioService);
		
		minioService.upload(Path.of("test.txt"), fileStream, ContentType.TEXT_PLAIN);
		System.out.println("Hello World!");
	}
}
