package mops.hhu.de.rheinjug1.praxis;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SuppressWarnings("PMD")
@SpringBootTest
@AutoConfigureMockMvc
class PraxisApplicationTests {

  @Autowired MockMvc mvc;

  @Test
  void contextLoads() {}

  @Test
  void testUebersicht() throws Exception {
    mvc.perform(get("/")).andExpect(status().is3xxRedirection());
  }
}
