package mops.hhu.de.rheinjug1.praxis.domain.receipt;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
<<<<<<< HEAD:src/test/java/mops/hhu/de/rheinjug1/praxis/domain/receipt/ReceiptPrintServiceTest.java
=======
import mops.hhu.de.rheinjug1.praxis.models.Receipt;
import mops.hhu.de.rheinjug1.praxis.services.receipt.ReceiptPrintService;
import mops.hhu.de.rheinjug1.praxis.utils.FileUtils;
import org.bouncycastle.util.encoders.Base64;
>>>>>>> master:src/test/java/mops/hhu/de/rheinjug1/praxis/services/ReceiptPrintServiceTest.java
import org.junit.jupiter.api.Test;

class ReceiptPrintServiceTest {

<<<<<<< HEAD:src/test/java/mops/hhu/de/rheinjug1/praxis/domain/receipt/ReceiptPrintServiceTest.java
  private static final String TEST_FILE_CONTENT =
      "email: testEmail\n"
          + "meetupId: 12345\n"
          + "meetupTitle: testMeetupTitle\n"
          + "meetupType: ENTWICKELBAR\n"
          + "name: testName\n"
          + "signature: testSignature\n";

=======
>>>>>>> master:src/test/java/mops/hhu/de/rheinjug1/praxis/services/ReceiptPrintServiceTest.java
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
<<<<<<< HEAD:src/test/java/mops/hhu/de/rheinjug1/praxis/domain/receipt/ReceiptPrintServiceTest.java
    final String path = receiptPrintService.printReceipt(receipt);
    assertThat(Files.readString(Paths.get(path))).isEqualTo(TEST_FILE_CONTENT);
=======
    final String path = receiptPrintService.printReceiptAndReturnPath(receipt);

    final String expectedMailText = FileUtils.readStringFromFile("mail/testEmailAttachment.txt");
    final String actualMailText = new String(Base64.decode(Files.readString(Paths.get(path))));

    assertThat(actualMailText).isEqualTo(expectedMailText);
>>>>>>> master:src/test/java/mops/hhu/de/rheinjug1/praxis/services/ReceiptPrintServiceTest.java
  }
}
