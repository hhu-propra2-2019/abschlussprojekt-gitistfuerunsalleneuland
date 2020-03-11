package mops.hhu.de.rheinjug1.praxis.services;

import java.io.IOException;

import com.jlefebure.spring.boot.minio.MinioException;
import com.jlefebure.spring.boot.minio.MinioService;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Path;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.entity.ContentType;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

@Service
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
	
	public void getObject(String object, HttpServletResponse response) throws MinioException, IOException {
        InputStream inputStream = minioService.get(Path.of(object));

        // Set the content type and attachment header.
        response.addHeader("Content-disposition", "attachment;filename=" + object);
        response.setContentType(URLConnection.guessContentTypeFromName(object));

        // Copy the stream to the response's output stream.
        IOUtils.copy(inputStream, response.getOutputStream());
        response.flushBuffer();
    }

}
