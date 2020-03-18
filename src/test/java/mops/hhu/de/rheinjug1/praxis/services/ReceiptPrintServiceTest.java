package mops.hhu.de.rheinjug1.praxis.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.models.Receipt;
import mops.hhu.de.rheinjug1.praxis.services.receipt.ReceiptPrintService;
import org.junit.jupiter.api.Test;

class ReceiptPrintServiceTest {

  @Test
  void receiptGetsPrintedCorrectlyToTemporaryFile() throws IOException {
    final ReceiptPrintService receiptPrintService = new ReceiptPrintService();
    final Receipt receipt =
        new Receipt("testName", "testEmail", 1L, "Titel", MeetupType.ENTWICKELBAR, "OEUIc5654eut");

    final String actualPath = receiptPrintService.printReceipt(receipt);

    final List<String> expected =
        Arrays.asList(
            "Name: testName",
            "Email: testEmail",
            "Veranstaltungs-ID: 1",
            "Titel: Titel",
            "Typ: Entwickelbar",
            "OEUIc5654eut");

    final List<String> actual = Files.readAllLines(Paths.get(actualPath));

    assertThat(actual).isEqualTo(expected);
  }
}
