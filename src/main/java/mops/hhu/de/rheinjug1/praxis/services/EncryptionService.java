package mops.hhu.de.rheinjug1.praxis.services;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import lombok.NoArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class EncryptionService {

  private KeyService keyService;

  @Autowired
  public EncryptionService(final KeyService keyService) {
    this.keyService = keyService;
  }

  private String createHashValue(
      final MeetupType meetupType, final long meetupId, final String name, final String email) {
    return meetupType.getLabel() + meetupId + name + email;
  }

  public String sign(
      final MeetupType meetupType, final long meetupId, final String name, final String email)
      throws NoSuchAlgorithmException, IOException, InvalidKeyException, KeyStoreException,
          UnrecoverableEntryException, CertificateException, SignatureException {

    final KeyPair pair = keyService.getKeyPairFromKeyStore();
    final PrivateKey privateKey = pair.getPrivate();

    final Signature sign = Signature.getInstance("SHA256withRSA");
    sign.initSign(privateKey);

    final String hashValue = createHashValue(meetupType, meetupId, name, email);
    sign.update(hashValue.getBytes(StandardCharsets.UTF_8));

    return Base64.toBase64String(sign.sign());
  }
}
