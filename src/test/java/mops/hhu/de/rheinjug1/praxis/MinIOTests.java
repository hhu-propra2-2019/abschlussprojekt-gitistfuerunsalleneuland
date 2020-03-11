package mops.hhu.de.rheinjug1.praxis;

import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.jlefebure.spring.boot.minio.MinioService;

import io.minio.MinioClient;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;

@RunWith(SpringRunner.class)
@WebMvcTest
public class MinIOTests {
	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	MinioService minioService;
	
	@MockBean
	MinioClient minioClient;
	
	@Test
	void getFiles() throws Exception {
		mvc.perform(get("/files/")).andExpect(status().isOk());
		
	}
	
	
	
	@Test
	void uploadFile() throws Exception {
		File newFile = new File("src/test/resources/test.txt");
		InputStream fileStream = new FileInputStream(newFile);
		
		minioService.upload(Path.of("src/test/resources/test.txt"), fileStream, ContentType.TEXT_PLAIN);
		mvc.perform(get("/files/test.txt")).andExpect(status().isOk());
	}

}
