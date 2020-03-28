package mops.hhu.de.rheinjug1.praxis.domain.receipt;

import static mops.hhu.de.rheinjug1.praxis.TestHelper.sampleReceipt;
import static org.mockito.Mockito.*;

import java.io.IOException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import mops.hhu.de.rheinjug1.praxis.adapters.mail.MailServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
class ReceiptSendServiceTest {

  @Autowired ReceiptSendService receiptSendService;
  @Autowired FileHandler fileHandler;
  @Autowired Encoder encoder;

  @Test
  void sendRealMail() throws MessagingException, IOException {
    receiptSendService.sendReceipt(sampleReceipt(), "rheinjughhu@gmail.com");
  }

  @Test
  void SendMockMail() throws IOException, MessagingException {
    final JavaMailSender mailSender = mock(JavaMailSender.class);
    final MimeMessage mimeMessage = mock(MimeMessage.class);
    when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
    final MailService mailService = new MailServiceImpl(mailSender);
    final ReceiptSendService receiptSendService =
        new ReceiptSendService(mailService, new ReceiptPrintService(fileHandler, encoder));

    receiptSendService.sendReceipt(sampleReceipt(), "rheinjughhu@gmail.com");

    verify(mailSender).send(mimeMessage);
  }
}
