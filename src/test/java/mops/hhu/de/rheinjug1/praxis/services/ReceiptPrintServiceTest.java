package mops.hhu.de.rheinjug1.praxis.services;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.models.Receipt;
import mops.hhu.de.rheinjug1.praxis.services.receipt.ReceiptPrintService;
import mops.hhu.de.rheinjug1.praxis.utils.FileUtils;
import org.bouncycastle.util.encoders.Base64;
import org.junit.jupiter.api.Test;

class ReceiptPrintServiceTest {

  @Test
  void writeYml() throws IOException {

    final Receipt receipt =
        Receipt.builder()
            .meetupId(12_345L)
            .name("testName")
            .email("testEmail")
            .meetupTitle("testMeetupTitle")
            .signature("testSignature")
            .meetupType(MeetupType.ENTWICKELBAR)
            .build();
    final ReceiptPrintService receiptPrintService = new ReceiptPrintService();
    final String path = receiptPrintService.printReceiptAndReturnPath(receipt);

    final String expectedMailText = FileUtils.readStringFromFile("mail/testEmailAttachment.txt");

    final String actualMailText = new String(Base64.decode(Files.readString(Paths.get(path))));

    assertThat(actualMailText).isEqualTo(expectedMailText);
  }
}
