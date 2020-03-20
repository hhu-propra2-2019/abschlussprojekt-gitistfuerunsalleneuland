package mops.hhu.de.rheinjug1.praxis.services;

import static org.junit.Assert.*;

import java.io.IOException;
import mops.hhu.de.rheinjug1.praxis.domain.Receipt;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
public class FileReaderServiceTests {

  private static final String VALID_FILE =
      "Name: file\r\n"
          + "Veranstaltungs-ID: 123\r\n"
          + "Titel: abc\r\n"
          + "Typ: Rheinjug\r\n"
          + "signature\r\n";
  private static final String INVALID_FILE =
      "Name:\r\n" + "Veranstaltungs-ID:\r\n" + "Titel:\r\n" + "Typ:\r\n";
  @Autowired private FileReaderService fileReaderService;

  @Test
  public void validFileGenereatesCorrectReceipt() {
    final MultipartFile file = new MockMultipartFile("validFile", VALID_FILE.getBytes());
    final Receipt receipt = new Receipt();
    receipt.setMeetupId(123);
    receipt.setType("Rheinjug");
    receipt.setSignature("signature");
    try {
      assertEquals(
          "read file didn't equal expected receipt", receipt, fileReaderService.read(file));
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void invalidFileDoesNotBecomeReceipt() {
    final MultipartFile file = new MockMultipartFile("invalidFile", INVALID_FILE.getBytes());
    final Receipt receipt = new Receipt();
    receipt.setMeetupId(123);
    receipt.setType("Rheinjug");
    receipt.setSignature("signature");
    boolean thrown = false;
    try {
      fileReaderService.read(file);
    } catch (IOException e) {
      thrown = true;
    }
    assertTrue("invalid file generated a receipt", thrown);
  }

  @Test
  public void validFileIsAccepted() {
    final MultipartFile file = new MockMultipartFile("validFile", VALID_FILE.getBytes());
    boolean thrown = true;
    try {
      fileReaderService.read(file);
    } catch (IOException e) {
      thrown = false;
    }
    assertTrue("valid file got rejected", thrown);
  }
}
