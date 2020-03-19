package mops.hhu.de.rheinjug1.praxis.services;

import java.io.IOException;
import javax.mail.MessagingException;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.models.Receipt;
import mops.hhu.de.rheinjug1.praxis.services.receipt.ReceiptPrintService;
import mops.hhu.de.rheinjug1.praxis.services.receipt.ReceiptSendService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReceiptSendServiceTest {

  @Autowired ReceiptSendService receiptSendService;

  @Test
  void sendRealMail() throws MessagingException, IOException {
    final Receipt receipt =
        new Receipt("TestName", "TestEmail", 1L, "Titel", MeetupType.ENTWICKELBAR, "OEUIc5654eut");
    receiptSendService.sendReceipt(receipt, "rheinjughhu@gmail.com");
  }
}
