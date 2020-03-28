package mops.hhu.de.rheinjug1.praxis.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.mail.MessagingException;
import mops.hhu.de.rheinjug1.praxis.domain.certification.CertificationSendService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CertificationSendServiceTest {

  @Autowired CertificationSendService certificationSendService;

  @Test
  public void sendTestCertificate() throws IOException, MessagingException {
    final byte[] certificate = Files.readAllBytes(Paths.get("src/test/resources/test_schein.docx"));
    certificationSendService.sendCertificate(certificate, "rheinjughhu@gmail.com");
  }
}
