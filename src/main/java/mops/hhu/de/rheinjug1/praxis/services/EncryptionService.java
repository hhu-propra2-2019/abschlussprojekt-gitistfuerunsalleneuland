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

  @Autowired private KeyService keyService;

  private String createHashValue(
      final MeetupType meetupType, final long meetupId, final long keycloakId) {
    return meetupType.getLabel() + meetupId + keycloakId;
  }

  public String sign(final MeetupType meetupType, final long meetupId, final long keycloakId)
      throws NoSuchAlgorithmException, IOException, InvalidKeyException, SignatureException,
          KeyStoreException, UnrecoverableEntryException, CertificateException {

    final KeyPair pair = keyService.getKeyPairFromKeyStore();
    final PrivateKey privateKey = pair.getPrivate();

    final Signature sign = Signature.getInstance("SHA256withRSA");
    sign.initSign(privateKey);

    final String hashValue = createHashValue(meetupType, meetupId, keycloakId);
    sign.update(hashValue.getBytes(StandardCharsets.UTF_8));

    return Base64.toBase64String(sign.sign());
  }
}
