package mops.hhu.de.rheinjug1.praxis.domain;

import static mops.hhu.de.rheinjug1.praxis.TestHelper.sampleEntwickelbarReceipt;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.entities.Receipt;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.services.ReceiptPrintService;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.services.ReceiptReadService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReceiptPrintAndReadServiceTest {

  @Autowired ReceiptPrintService receiptPrintService;
  @Autowired ReceiptReadService receiptReadService;

  @Test
  void givesBackCorrectReceipt() throws IOException, SignatureException, InvalidKeyException {
    final Receipt receipt = sampleEntwickelbarReceipt();
    final String path = receiptPrintService.printReceiptAndReturnPath(receipt);
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
