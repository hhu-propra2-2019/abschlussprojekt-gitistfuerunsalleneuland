package mops.hhu.de.rheinjug1.praxis;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.models.ReceiptDTO;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;

public class YAMLTest {

  private static final String TEST_FILE_CONTENT =
      "!!mops.hhu.de.rheinjug1.praxis.models.ReceiptDTO {email: testEmail, meetupId: 12345,\n"
          + "  meetupType: ENTWICKELBAR, name: testName, signature: testSignature, title: testMeetupTitle}\n";

  @Test
  void writeYml() throws IOException {
    final Yaml yaml = new Yaml();
    final String path = File.createTempFile("receipt", ".tmp").getAbsolutePath();
    final Writer fileWriter = new FileWriter(path);

    final long meetupId = 12_345L;
    final String name = "testName";
    final String email = "testEmail";
    final String meetupTitle = "testMeetupTitle";
    final String signature = "testSignature";
    final MeetupType meetupType = MeetupType.ENTWICKELBAR;

    final ReceiptDTO receipt =
        new ReceiptDTO(name, email, meetupId, meetupTitle, meetupType, signature);
    yaml.dump(receipt, fileWriter);

    assertThat(Files.readString(Paths.get(path))).isEqualTo(TEST_FILE_CONTENT);
  }

  @Test
  void readYml() throws IOException {
    final Yaml yaml = new Yaml();
    final String path = File.createTempFile("receipt", ".tmp").getAbsolutePath();
    final Writer fileWriter = new FileWriter(path);
    final BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
    bufferedWriter.write(TEST_FILE_CONTENT);
    bufferedWriter.close();

    final Reader fileReader = new FileReader(path);

    final ReceiptDTO receiptDTO = (ReceiptDTO) yaml.load(fileReader);

    assertThat(receiptDTO.getName()).isEqualTo("testName");
  }
}
