package mops.hhu.de.rheinjug1.praxis.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.models.Receipt;
import mops.hhu.de.rheinjug1.praxis.services.receipt.ReceiptPrintService;
import org.junit.jupiter.api.Test;

class ReceiptPrintServiceTest {

  private static final String TEST_FILE_CONTENT =
      "!!mops.hhu.de.rheinjug1.praxis.models.Receipt {email: testEmail, meetupId: 12345,\n"
          + "  meetupTitle: testMeetupTitle, meetupType: ENTWICKELBAR, name: testName, signature: testSignature}\n";

  @Test
  void writeYml() throws IOException {

    final long meetupId = 12_345L;
    final String name = "testName";
    final String email = "testEmail";
    final String meetupTitle = "testMeetupTitle";
    final String signature = "testSignature";
    final MeetupType meetupType = MeetupType.ENTWICKELBAR;

    final Receipt receipt = new Receipt(name, email, meetupId, meetupTitle, meetupType, signature);
    final ReceiptPrintService receiptPrintService = new ReceiptPrintService();
    final String path = receiptPrintService.printReceipt(receipt);

    assertThat(Files.readString(Paths.get(path))).isEqualTo(TEST_FILE_CONTENT);
  }
}
