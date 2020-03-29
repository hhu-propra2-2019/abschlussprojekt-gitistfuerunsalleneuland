package mops.hhu.de.rheinjug1.praxis.domain.receipt;

import static mops.hhu.de.rheinjug1.praxis.TestHelper.sampleEntwickelbarReceipt;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import mops.hhu.de.rheinjug1.praxis.adapters.mail.MailServiceImpl;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.interfaces.Encoder;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.interfaces.FileHandler;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.interfaces.MailService;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.interfaces.ReceiptStringConverter;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.services.ReceiptPrintService;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.services.ReceiptSendService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
class ReceiptSendServiceTest {

  @Autowired ReceiptSendService receiptSendService;
  @Autowired FileHandler fileHandler;
  @Autowired Encoder encoder;
  @Autowired ReceiptStringConverter converter;

  @Test
  void sendRealMail()
      throws MessagingException, IOException, SignatureException, InvalidKeyException {
    receiptSendService.sendReceipt(sampleEntwickelbarReceipt(), "rheinjughhu@gmail.com");
  }

  @Test
  void SendMockMail()
      throws IOException, MessagingException, SignatureException, InvalidKeyException {
    final JavaMailSender mailSender = mock(JavaMailSender.class);
    final MimeMessage mimeMessage = mock(MimeMessage.class);
    when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
    final MailService mailService = new MailServiceImpl(mailSender);
    final ReceiptSendService receiptSendService =
        new ReceiptSendService(
            mailService, new ReceiptPrintService(fileHandler, encoder, converter));

    receiptSendService.sendReceipt(sampleEntwickelbarReceipt(), "rheinjughhu@gmail.com");
    verify(mailSender).send(mimeMessage);
  }
}
