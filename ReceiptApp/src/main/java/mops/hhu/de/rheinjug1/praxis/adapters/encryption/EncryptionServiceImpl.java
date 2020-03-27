package mops.hhu.de.rheinjug1.praxis.adapters.encryption;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import lombok.NoArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.EncryptionService;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class EncryptionServiceImpl implements EncryptionService {

  private KeyService keyService;

  @Autowired
  public EncryptionServiceImpl(final KeyService keyService) {
    this.keyService = keyService;
  }

  @Override
  public String sign(
      final MeetupType meetupType, final long meetupId, final String name, final String email)
      throws NoSuchAlgorithmException, IOException, InvalidKeyException, KeyStoreException,
          UnrecoverableEntryException, CertificateException, SignatureException {

    final KeyPair pair = keyService.getKeyPairFromKeyStore();
    final PrivateKey privateKey = pair.getPrivate();

    final Signature signature = Signature.getInstance("SHA256withRSA");
    signature.initSign(privateKey);

    final String hashValue = createHashValue(meetupType, meetupId, name, email);
    signature.update(hashValue.getBytes(StandardCharsets.UTF_8));

    return Base64.toBase64String(signature.sign());
  }

  private String createHashValue(
      final MeetupType meetupType, final long meetupId, final String name, final String email) {
    return meetupType.getLabel() + meetupId + name + email;
  }

  private boolean isVerified(
          final String signaturetoVerify, final PublicKey publicKey, final String hashValue)
          throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {

    final Signature sign = Signature.getInstance("SHA256withRSA");
    sign.initVerify(publicKey);
    final byte[] hashValueBytes = hashValue.getBytes(StandardCharsets.UTF_8);
    sign.update(hashValueBytes);
    return sign.verify(signaturetoVerify.getBytes());
  }
}
