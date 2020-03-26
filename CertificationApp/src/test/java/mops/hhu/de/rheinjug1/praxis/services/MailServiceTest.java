package mops.hhu.de.rheinjug1.praxis.services;

import javax.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MailServiceTest {

  @Autowired MailService mailService;

  @Test
  void sendMailWithReadme() throws MessagingException {
    final String to = "rheinjughhu@gmail.com";
    final String subject = "CertificationAppMailServiceTest";
    final String text = "This is a test";
    final String pathToAttachment = "README.adoc";
    final String filename = "Your File";

    mailService.sendMailWithAttachment(to, subject, text, pathToAttachment, filename);
  }
}
