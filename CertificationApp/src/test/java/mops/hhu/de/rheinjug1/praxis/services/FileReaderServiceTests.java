package mops.hhu.de.rheinjug1.praxis.services;

import static org.junit.Assert.*;

import java.io.IOException;
import mops.hhu.de.rheinjug1.praxis.domain.Receipt;
import org.junit.Before;
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
          ("Name: file\r\n"
                  + "Veranstaltungs-ID: 123\r\n"
                  + "Titel: abc\r\n"
                  + "Typ: Rheinjug\r\n"
                  + "signature\r\n")
              .getBytes());
  private final MultipartFile invalidFile =
      new MockMultipartFile(
          "validFile",
          ("Name:\r\n" + "Veranstaltungs-ID:\r\n" + "Titel:\r\n" + "Typ:\r\n").getBytes());
  private final Receipt receipt = new Receipt();

  @Autowired private FileReaderService fileReaderService;

  @Before
  public void setReceipt() {
    receipt.setMeetupId(123);
    receipt.setType("Rheinjug");
    receipt.setSignature("signature");
  }

  @Test
  public void validFileGenereatesCorrectReceipt() {
    try {
      assertEquals(
          "read file didn't equal expected receipt", receipt, fileReaderService.read(validFile));
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
