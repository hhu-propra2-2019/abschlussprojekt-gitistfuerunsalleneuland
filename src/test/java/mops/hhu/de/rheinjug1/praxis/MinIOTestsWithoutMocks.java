package mops.hhu.de.rheinjug1.praxis;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;

import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;

import com.jlefebure.spring.boot.minio.MinioService;


public class MinIOTestsWithoutMocks {
	

	private MinioService minioService = new MinioService();

	
	@Test
	void uploadFile() throws Exception {
		File newFile = new File("src/test/resources/test.txt");
		InputStream fileStream = new FileInputStream(newFile);
		
		System.out.println(minioService);
		
		minioService.upload(Path.of("src/test/resources/test.txt"), fileStream, ContentType.TEXT_PLAIN);
	}


}
