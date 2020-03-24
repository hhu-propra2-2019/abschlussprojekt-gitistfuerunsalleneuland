package mops.hhu.de.rheinjug1.praxis.domain.receipt;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.*;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.Receipt;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.ReceiptReadService;
import org.junit.jupiter.api.Test;

public class ReceiptReadServiceTest {

  private static final String TEST_FILE_CONTENT =
      "email: testEmail\n"
          + "meetupId: 12345\n"
          + "meetupTitle: testMeetupTitle\n"
          + "meetupType: ENTWICKELBAR\n"
          + "name: testName\n"
          + "signature: testSignature\n";

  @Test
  void readYml() throws IOException {

    final String path = File.createTempFile("receipt", ".tmp").getAbsolutePath();
    try (Writer fileWriter = new FileWriter(path);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        Reader fileReader = new FileReader(path)) {
      bufferedWriter.write(TEST_FILE_CONTENT);
      bufferedWriter.close();

      final ReceiptReadService receiptReadService = new ReceiptReadService();
      final Receipt receipt = receiptReadService.read(path);

      assertThat(receipt.getName()).isEqualTo("testName");
    }
  }
}
