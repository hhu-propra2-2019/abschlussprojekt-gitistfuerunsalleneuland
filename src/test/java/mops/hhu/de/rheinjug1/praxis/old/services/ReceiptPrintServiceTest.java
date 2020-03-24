package mops.hhu.de.rheinjug1.praxis.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.Receipt;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.ReceiptPrintService;
import org.junit.jupiter.api.Test;

class ReceiptPrintServiceTest {

  private static final String TEST_FILE_CONTENT =
      "email: testEmail\n"
          + "meetupId: 12345\n"
          + "meetupTitle: testMeetupTitle\n"
          + "meetupType: ENTWICKELBAR\n"
          + "name: testName\n"
          + "signature: testSignature\n";

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
    final String path = receiptPrintService.printReceipt(receipt);
    assertThat(Files.readString(Paths.get(path))).isEqualTo(TEST_FILE_CONTENT);
  }
}
