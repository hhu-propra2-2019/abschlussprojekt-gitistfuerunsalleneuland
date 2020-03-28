package mops.hhu.de.rheinjug1.praxis.adapters.encryption;

import static mops.hhu.de.rheinjug1.praxis.TestHelper.invalidEntwickelbarReceipt;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.security.*;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.entities.Receipt;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.interfaces.EncryptionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EncryptionServiceTest {
  @Autowired private EncryptionService encryptionService;

  @Test
  void encryptedReceiptIsValid()
      throws InvalidKeyException, IOException, SignatureException, NoSuchAlgorithmException {
    final Receipt receipt = invalidEntwickelbarReceipt();

    encryptionService.sign(receipt);
    assertTrue(encryptionService.isVerified(receipt));
  }
}
