package mops.hhu.de.rheinjug1.praxis.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.Receipt;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.ReceiptPrintService;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.ReceiptReadService;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReceiptPrintAndReadServiceTest {

  @Autowired ReceiptPrintService receiptPrintService;
  @Autowired ReceiptReadService receiptReadService;

  @Test
  void givesBackCorrectReceipt() throws IOException {

    final Receipt receipt =
        Receipt.builder()
            .meetupId(12_345L)
            .name("testName")
            .email("testEmail")
            .meetupTitle("testMeetupTitle")
            .signature("testSignature".getBytes())
            .meetupType(MeetupType.ENTWICKELBAR)
            .build();

    final String path = receiptPrintService.printReceiptAndGetPath(receipt);
    assertThat(receiptReadService.read(path)).isEqualTo(receipt);
  }

  @Test
  void givesBackCorrectStringFromFile() throws IOException {
    final Path path = Path.of(File.createTempFile("receipt", ".tmp").getAbsolutePath());
    final String s = "Hello World!";
    Files.writeString(path, s);
    assertThat(Files.readString(path)).isEqualTo(s);
  }
}
