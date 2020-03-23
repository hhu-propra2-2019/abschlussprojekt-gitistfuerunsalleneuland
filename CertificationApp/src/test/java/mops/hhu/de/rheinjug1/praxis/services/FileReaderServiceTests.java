package mops.hhu.de.rheinjug1.praxis.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.io.IOException;
import mops.hhu.de.rheinjug1.praxis.MeetupType;
import mops.hhu.de.rheinjug1.praxis.domain.Receipt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
public class FileReaderServiceTests {

  private final MultipartFile validFile =
      new MockMultipartFile(
          "validFile",
          ("meetupId: 12345\n" + "meetupType: ENTWICKELBAR\n" + "signature: OEUIc5654eut\n")
              .getBytes());
  private final MultipartFile invalidFile =
      new MockMultipartFile(
          "invalidFile",
          ("!!mops.hhu.de.rheinjug1.praxis.models.Receipt {email: TestEmail, meetupId: , meetupTitle: Titel,\r\n"
                  + "  meetupType: ENTWICKELBAR, name: TestName, signature: OEUIc5654eut}\r\n"
                  + "")
              .getBytes());
  private final Receipt receipt = new Receipt();

  @Autowired private YamlReceiptReader fileReaderService;

  @BeforeEach
  public void setReceipt() {
    receipt.setMeetupId(12345);
    receipt.setMeetupType(MeetupType.ENTWICKELBAR);
    receipt.setSignature("OEUIc5654eut");
  }

  @Test
  public void validFileGenereatesCorrectReceipt() {
    try {
      assertThat(fileReaderService.read(validFile)).isEqualTo(receipt);
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void invalidFileDoesNotBecomeReceipt() {
    boolean thrown = false;
    try {
      fileReaderService.read(invalidFile);
    } catch (IOException e) {
      thrown = true;
    }
    assertTrue("invalid file generated a receipt", thrown);
  }

  @Test
  public void validFileIsAccepted() {
    boolean thrown = true;
    try {
      fileReaderService.read(validFile);
    } catch (IOException e) {
      thrown = false;
    }
    assertTrue("valid file got rejected", thrown);
  }
}
