package mops.hhu.de.rheinjug1.praxis.services;

import static org.junit.Assert.*;

import java.io.IOException;
import mops.hhu.de.rheinjug1.praxis.domain.Receipt;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.exceptions.Base64Exception;
import mops.hhu.de.rheinjug1.praxis.interfaces.ReceiptReaderInterface;

import org.junit.Ignore;
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
          ("ZW1haWw6IFRlc3RFbWFpbAptZWV0dXBJZDogMQptZWV0dXBUaXRsZTogVGl0ZWwKbWVldHVwVHlwZTogRU5UV0lDS0VMQkFSCm5hbWU6IFRlc3ROYW1lCnNpZ25hdHVyZTogT0VVSWM1NjU0ZXV0Cg==")
              .getBytes());
  private final MultipartFile invalidFile =
      new MockMultipartFile(
          "invalidFile",
          ("ZW1haWw6IFRlc3RFbWFpbAptZWV0dXBJZDogMQptZWV0dXBUaXRsZTogVGl0ZWwKbWVldHVwVHlwZTogRU5UV0lDS0VMQkFSCm5hbWU6IFRc3ROYW1lCnNpZ25hdHVyZTogT0VVSWM1NjU0ZXV0Cg==")
              .getBytes());
  private final Receipt receipt = new Receipt();

  @Autowired private ReceiptReaderInterface fileReaderService = new YamlReceiptReader();

  @BeforeEach
  public void setReceipt() {
    receipt.setMeetupId((long) 1);
    receipt.setMeetupType(MeetupType.ENTWICKELBAR);
    receipt.setSignature("OEUIc5654eut");
  }

  @Test
  public void validFileGenereatesCorrectReceipt() {
    try {
    	assertEquals(
          "read file didn't equal expected receipt", receipt, fileReaderService.read(validFile));
    } catch (IOException | Base64Exception e) {
      fail();
	}
  }

  @Test
  public void invalidFileDoesNotBecomeReceipt() {
    boolean thrown = false;
    try {
    	fileReaderService.read(invalidFile);
    } catch (IOException | Base64Exception e) {
      thrown = true;
	}
    assertTrue("invalid file generated a receipt", thrown);
  }

  @Test
  public void validFileIsAccepted() {
    boolean thrown = true;
    try {
      fileReaderService.read(validFile);
    } catch (IOException | Base64Exception e) {
      thrown = false;
    }
    assertTrue("valid file got rejected", thrown);
  }
}
