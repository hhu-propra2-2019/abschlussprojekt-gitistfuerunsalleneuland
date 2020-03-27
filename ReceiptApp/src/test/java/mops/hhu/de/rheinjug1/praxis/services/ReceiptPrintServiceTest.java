package mops.hhu.de.rheinjug1.praxis.services;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.models.Receipt;
import mops.hhu.de.rheinjug1.praxis.services.receipt.ReceiptPrintService;
import org.bouncycastle.util.encoders.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReceiptPrintServiceTest {

  @Autowired ReceiptPrintService receiptPrintService;

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

    final String path = receiptPrintService.printReceiptAndReturnPath(receipt);
    final String actualMailText =
        new String(Base64.decode(Files.readString(Paths.get(path))), StandardCharsets.UTF_8);

    assertThat(actualMailText, containsString("12345"));
    assertThat(actualMailText, containsString("testName"));
    assertThat(actualMailText, containsString("testEmail"));
    assertThat(actualMailText, containsString("testMeetupTitle"));
    assertThat(actualMailText, containsString("testSignature"));
    assertThat(actualMailText, containsString("ENTWICKELBAR"));
  }
}
