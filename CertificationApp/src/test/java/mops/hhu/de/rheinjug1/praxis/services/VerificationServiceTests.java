package mops.hhu.de.rheinjug1.praxis.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import mops.hhu.de.rheinjug1.praxis.domain.Receipt;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import org.bouncycastle.util.encoders.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VerificationServiceTests {

  @Autowired private KeyService keyService;
  @Autowired private VerificationService verificationService;

  static Receipt validReceipt;
  static Receipt invalidReceipt;

  @BeforeEach
  public void setUp()
      throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
          UnrecoverableEntryException, IOException, InvalidKeyException, SignatureException {

    final String hash = MeetupType.ENTWICKELBAR.getLabel() + 1 + "name" + "email";
    final KeyPair pair = keyService.getKeyPairFromKeyStore();
    final PrivateKey privateKey = pair.getPrivate();
    final Signature sign = Signature.getInstance("SHA256withRSA");
    sign.initSign(privateKey);
    sign.update(hash.getBytes(StandardCharsets.UTF_8));

    validReceipt =
        new Receipt(
            "name",
            "email",
            (long) 1,
            "meetupTitle",
            MeetupType.ENTWICKELBAR,
            Base64.toBase64String(sign.sign()));

    invalidReceipt =
        new Receipt(
            "name",
            "falseEmail",
            (long) 1,
            "meetupTitle",
            MeetupType.ENTWICKELBAR,
            Base64.toBase64String(sign.sign()));
  }

  @Test
  public void verifyValidReceipt()
      throws InvalidKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException,
          UnrecoverableEntryException, SignatureException, IOException {
    assertTrue("Verification worked", verificationService.isSignatureValid(validReceipt));
  }

  @Test
  public void verifyInvalidReceipt()
      throws InvalidKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException,
          UnrecoverableEntryException, SignatureException, IOException {
    assertFalse("Verification did not work", verificationService.isSignatureValid(invalidReceipt));
  }
}
