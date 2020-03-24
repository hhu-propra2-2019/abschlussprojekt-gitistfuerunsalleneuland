package mops.hhu.de.rheinjug1.praxis.services;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VerificationServiceTests {

  @Autowired private KeyService keyService;
  @Autowired private VerificationService verificationService;
  Receipt receipt;

  @Before
  public void setup() {

    try {
      String hash = "ENTWICKELBAR" + 1 + "name" + "email";

      final KeyPair pair = keyService.getKeyPairFromKeyStore();
      final PrivateKey privateKey = pair.getPrivate();

      final Signature sign = Signature.getInstance("SHA256withRSA");
      sign.initSign(privateKey);
      sign.update(hash.getBytes(StandardCharsets.UTF_8));
      receipt =
          new Receipt(
              "name",
              "email",
              (long) 1,
              "meetupTitle",
              MeetupType.ENTWICKELBAR,
              Base64.toBase64String(sign.sign()));
    } catch (InvalidKeyException | KeyStoreException | NoSuchAlgorithmException | CertificateException
			| UnrecoverableEntryException | SignatureException | IOException e) {
      fail("Encryption-Error");
    }
  }
  
  @Test
  public void doesVerificationWork() throws InvalidKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException, SignatureException, IOException {
		assertTrue("Verifcation failed", verificationService.isSignatureValid(receipt));
  }
}
