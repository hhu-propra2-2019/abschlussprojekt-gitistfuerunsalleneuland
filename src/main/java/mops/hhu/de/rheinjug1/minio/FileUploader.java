package mops.hhu.de.rheinjug1.minio;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;

import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;

import com.jlefebure.spring.boot.minio.MinioService;


public class FileUploader {
	
	@Autowired
	private MinioService minioService;
	

	
	
	public void uploadFile() throws Exception {
		File newFile = new File("src/test/resources/test.txt");
		InputStream fileStream = new FileInputStream(newFile);
		
		minioService.upload(Path.of("src/test/resources/test.txt"), fileStream, ContentType.TEXT_PLAIN);
	}

}
