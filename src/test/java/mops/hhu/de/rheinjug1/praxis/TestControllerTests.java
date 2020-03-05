package mops.hhu.de.rheinjug1.praxis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.jlefebure.spring.boot.minio.MinioService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class TestControllerTests {
	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	MinioService minioService;
	
	@Test
	void minIOTest() throws Exception {
		mvc.perform(get("/")).andExpect(status().isOk());
		
	}

}
