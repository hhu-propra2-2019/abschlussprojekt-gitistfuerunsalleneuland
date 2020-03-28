package mops.hhu.de.rheinjug1.praxis.adapters.encryption;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.security.*;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.Receipt;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.ReceiptDTO;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EncryptionServiceTest {
  @Autowired private EncryptionService encryptionService;

  @Test
  void encryptedReceiptIsValid()
      throws InvalidKeyException, IOException, SignatureException, NoSuchAlgorithmException {
    final Receipt receipt =
        Receipt.createFromDTO(
            new ReceiptDTO(
                "name", "email", (long) 1, "meetupTitle", MeetupType.ENTWICKELBAR, null));

    encryptionService.sign(receipt);
    assertTrue(encryptionService.isVerified(receipt));
  }
}
