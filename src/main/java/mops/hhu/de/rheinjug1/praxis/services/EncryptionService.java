package mops.hhu.de.rheinjug1.praxis.services;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import lombok.NoArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class EncryptionService {

  @Value("${keystore.password}")
  private String keyStorePassword;

  @Value("${keystore.receipt.password}")
  private String keyPassword;

  @Value("${keystore.receipt.name}")
  private String keyName;

  private String createHashValue(
      final MeetupType meetupType, final long meetupId, final long keycloakId) {
    return meetupType.getLabel() + meetupId + keycloakId;
  }

  public String sign(final MeetupType meetupType, final long meetupId, final long keycloakId)
      throws NoSuchAlgorithmException, IOException, InvalidKeyException, SignatureException,
          KeyStoreException, UnrecoverableEntryException, CertificateException {

    final KeyPair pair = getKeyPairFromKeyStore();
    final PrivateKey privateKey = pair.getPrivate();

    final Signature sign = Signature.getInstance("SHA256withRSA");
    sign.initSign(privateKey);

    final String hashValue = createHashValue(meetupType, meetupId, keycloakId);
    sign.update(hashValue.getBytes(StandardCharsets.UTF_8));

    return Base64.toBase64String(sign.sign());
  }

  private KeyPair getKeyPairFromKeyStore()
      throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException,
          UnrecoverableEntryException {

    final KeyStore keyStore = KeyStore.getInstance("JCEKS");
    keyStore.load(new FileInputStream("src/main/resources/keystore.jks"), "password".toCharArray());

    final KeyStore.PasswordProtection keyPassword =
        new KeyStore.PasswordProtection(this.keyPassword.toCharArray());

    final KeyStore.PrivateKeyEntry privateKeyEntry =
        (KeyStore.PrivateKeyEntry) keyStore.getEntry(keyName, keyPassword);

    final Certificate cert = keyStore.getCertificate(keyName);
    final PublicKey publicKey = cert.getPublicKey();
    final PrivateKey privateKey = privateKeyEntry.getPrivateKey();

    return new KeyPair(publicKey, privateKey);
  }
}
