package mops.hhu.de.rheinjug1.praxis.services;

import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.models.Receipt;
import mops.hhu.de.rheinjug1.praxis.services.receipt.ReceiptPrintService;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class ReceiptReadServiceTest {

  private static final String TEST_FILE_CONTENT =
          "!!mops.hhu.de.rheinjug1.praxis.models.Receipt {email: testEmail, meetupId: 12345,\n"
                  + "  meetupTitle: testMeetupTitle, meetupType: ENTWICKELBAR, name: testName, signature: testSignature}\n";

  @Test
  void readYml() throws IOException {
    final Yaml yaml = new Yaml();
    final String path = File.createTempFile("receipt", ".tmp").getAbsolutePath();
    final Writer fileWriter = new FileWriter(path);
    final BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
    bufferedWriter.write(TEST_FILE_CONTENT);
    bufferedWriter.close();

    final Reader fileReader = new FileReader(path);

    final Receipt receiptDTO = (Receipt) yaml.load(fileReader);

    assertThat(receiptDTO.getName()).isEqualTo("testName");
  }
}
