package mops.hhu.de.rheinjug1.praxis.adapters.encryption;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeyConfiguration {

  @Value("${keystore.password}")
  private String keyStorePassword;

  @Value("${keystore.path}")
  private String keyStorePath;

  @Value("${keystore.receipt.password}")
  private String keyPassword;

  @Value("${keystore.receipt.name}")
  private String keyName;

  @Bean
  public KeyPair getKeyPairFromKeyStore()
      throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException,
          UnrecoverableEntryException {

    final KeyStore keyStore = KeyStore.getInstance("JCEKS");

    //noinspection LocalCanBeFinal
    try (FileInputStream keyFile = new FileInputStream(keyStorePath)) {
      keyStore.load(keyFile, keyStorePassword.toCharArray());
    }

    final PasswordProtection keyPassword = new PasswordProtection(this.keyPassword.toCharArray());

    final PrivateKeyEntry privateKeyEntry =
        (PrivateKeyEntry) keyStore.getEntry(keyName, keyPassword);

    final Certificate cert = keyStore.getCertificate(keyName);
    final PublicKey publicKey = cert.getPublicKey();
    final PrivateKey privateKey = privateKeyEntry.getPrivateKey();

    return new KeyPair(publicKey, privateKey);
  }

  @Bean
  Signature getSecuritySignature() throws NoSuchAlgorithmException {
    return Signature.getInstance("SHA256withRSA");
  }
}
