package mops.hhu.de.rheinjug1.praxis.services;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.mail.MessagingException;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.models.Receipt;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReceiptSendServiceTest {

  File file;
  String pathName;
  Path path;

  @Autowired ReceiptSendService receiptSendService;
  @Autowired ReceiptPrintService receiptPrintService;

  @BeforeEach
  void createFile() {
    file = new File("src/main/resources/Titel-Quittung.txt");
    pathName = "src/main/resources/Titel-Quittung.txt";
    path = Paths.get(pathName);
  }

  @Test
  void receiptSendTest() throws MessagingException {
    final Receipt receipt =
        new Receipt("TestName", "TestEmail", 1L, "Titel", MeetupType.ENTWICKELBAR, "OEUIc5654eut");
    receiptSendService.sendReceipt(receipt, "rheinjughhu@gmail.com");
  }

  @AfterEach
  void deleteFile() {
    file.delete();
  }
}
