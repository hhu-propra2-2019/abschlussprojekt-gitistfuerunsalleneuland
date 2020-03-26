package mops.hhu.de.rheinjug1.praxis.domain.receipt;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import org.junit.jupiter.api.Disabled;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

class ReceiptPrintServiceTest {

  @Disabled
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

    final String actualMailText = Files.readString(Paths.get(path));
    final String expectedMailText =
        Files.readString(Paths.get("src/main/resources/mail/testEmailAttachment.txt"));
    assertThat(actualMailText).isEqualTo(expectedMailText);
  }
}
