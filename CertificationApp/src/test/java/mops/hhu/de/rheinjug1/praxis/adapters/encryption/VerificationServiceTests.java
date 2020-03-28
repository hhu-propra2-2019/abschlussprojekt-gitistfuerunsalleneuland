package mops.hhu.de.rheinjug1.praxis.adapters.encryption;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import mops.hhu.de.rheinjug1.praxis.domain.certification.DuplicateSignatureException;
import mops.hhu.de.rheinjug1.praxis.domain.certification.SignatureDoesntMatchException;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.Receipt;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.ReceiptDTO;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import org.bouncycastle.util.encoders.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VerificationServiceTests {

  @Autowired private KeyPair keyPair;
  @Autowired private VerificationServiceImpl verificationService;
  @Autowired private Signature securitySignature;

  static Receipt validReceipt;
  static Receipt invalidReceipt;

  @BeforeEach
  public void setUp()
      throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
          UnrecoverableEntryException, IOException, InvalidKeyException, SignatureException {

    final String hash = MeetupType.ENTWICKELBAR.getLabel() + 1 + "name" + "email";
    final PrivateKey privateKey = keyPair.getPrivate();
    securitySignature.initSign(privateKey);
    securitySignature.update(hash.getBytes(StandardCharsets.UTF_8));

    validReceipt =
            Receipt.createFromDTO(new ReceiptDTO(
            "name",
            "email",
            (long) 1,
            "meetupTitle",
            MeetupType.ENTWICKELBAR,
            securitySignature.sign()));

    invalidReceipt =
            Receipt.createFromDTO(new ReceiptDTO(
            "name",
            "falseEmail",
            (long) 1,
            "meetupTitle",
            MeetupType.ENTWICKELBAR,
                    securitySignature.sign()));
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
