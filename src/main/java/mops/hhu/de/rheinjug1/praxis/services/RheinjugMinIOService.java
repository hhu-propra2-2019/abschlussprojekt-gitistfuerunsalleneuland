package mops.hhu.de.rheinjug1.praxis.services;

import java.io.IOException;

import com.jlefebure.spring.boot.minio.MinioException;
import com.jlefebure.spring.boot.minio.MinioService;
import java.io.InputStream;
import java.nio.file.Path;

import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public class RheinjugMinIOService {
	
	@Autowired
	MinioService minioService;
	
	public void upload(MultipartFile file) {
		InputStream inputStream = null;
		
		try {
			inputStream = file.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			minioService.upload(Path.of(file.getName()), inputStream, ContentType.TEXT_PLAIN);
		} catch (MinioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
