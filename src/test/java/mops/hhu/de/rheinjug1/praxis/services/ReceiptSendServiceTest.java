package mops.hhu.de.rheinjug1.praxis.services;

import java.io.IOException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.models.Receipt;
import mops.hhu.de.rheinjug1.praxis.services.receipt.ReceiptPrintService;
import mops.hhu.de.rheinjug1.praxis.services.receipt.ReceiptReadService;
import mops.hhu.de.rheinjug1.praxis.services.receipt.ReceiptSendService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

@SpringBootTest
class ReceiptSendServiceTest {

  @Autowired ReceiptSendService receiptSendService;

  @Test
  void sendRealMail() throws MessagingException, IOException {
    final Receipt receipt =
        new Receipt("TestName", "TestEmail", 1L, "Titel", MeetupType.ENTWICKELBAR, "OEUIc5654eut");
    receiptSendService.sendReceipt(receipt, "rheinjughhu@gmail.com");
  }

  @Test
  void SendMockMail() throws IOException, MessagingException {
    final Receipt receipt  =
            new Receipt("TestName", "TestEmail", 1L, "Titel", MeetupType.ENTWICKELBAR, "OEUIc5654eut");
    final JavaMailSender mailSender = mock(JavaMailSender.class);
    final MimeMessage mimeMessage = mock(MimeMessage.class);
    when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
    final MailService mailService = new MailService(mailSender);
    final ReceiptSendService receiptSendService = new ReceiptSendService(mailService, new ReceiptPrintService());

    receiptSendService.sendReceipt(receipt, "rheinjughhu@gmail.com");

    verify(mailSender).send(mimeMessage);
  }
}
