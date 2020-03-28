package mops.hhu.de.rheinjug1.praxis.adapters.encryption;

import static mops.hhu.de.rheinjug1.praxis.TestHelper.sampleEntwickelbarReceipt;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import mops.hhu.de.rheinjug1.praxis.domain.certification.DuplicateSignatureException;
import mops.hhu.de.rheinjug1.praxis.domain.certification.SignatureDoesntMatchException;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.entities.Receipt;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.interfaces.EncryptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VerificationServiceTests {

  @Autowired private VerificationServiceImpl verificationService;
  @Autowired private EncryptionService encryptionService;

  static Receipt validReceipt;
  static Receipt invalidReceipt;

  @BeforeEach
  public void setUp() throws IOException, InvalidKeyException, SignatureException {

    validReceipt = sampleEntwickelbarReceipt();
    invalidReceipt = validReceipt.clone();
    invalidReceipt.setEmail("fakeEmail");
  }

  @Test
  public void verifyValidReceipt()
      throws InvalidKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException,
          UnrecoverableEntryException, SignatureException, IOException {
    try {
      assertTrue("Verification worked", verificationService.isSignatureValid(validReceipt));
    } catch (DuplicateSignatureException e) {
      fail("wrong message: Duplicate Signature");
    } catch (SignatureDoesntMatchException e) {
      fail("wrong message: SignatureDoesntMatch");
    }
  }

  @Test
  public void verifyInvalidReceipt() {
    assertThrows(
        SignatureDoesntMatchException.class,
        () -> verificationService.isSignatureValid(invalidReceipt));
  }
}
