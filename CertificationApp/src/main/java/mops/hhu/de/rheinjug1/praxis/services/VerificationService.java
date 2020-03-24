package mops.hhu.de.rheinjug1.praxis.services;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import mops.hhu.de.rheinjug1.praxis.domain.Receipt;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.interfaces.ReceiptVerificationInterface;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationService implements ReceiptVerificationInterface {

  @Autowired private KeyService keyService;

  @Override
  public boolean isSignatureValid(final Receipt receipt)
      throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
          UnrecoverableEntryException, IOException, InvalidKeyException, SignatureException {

    final PublicKey publicKey = keyService.getKeyPairFromKeyStore().getPublic();

    final String name = receipt.getName();
    final long meetupId = receipt.getMeetupId();
    final MeetupType meetupType = receipt.getMeetupType();
    final String email = receipt.getEmail();
    final byte[] signature = Base64.decode(receipt.getSignature());
    final String hash = hash(meetupType, meetupId, name, email);
    return isVerified(signature, publicKey, hash);
  }

  private String hash(
      final MeetupType meetupType, final long meetupId, final String name, final String email) {
    return meetupType.getLabel() + meetupId + name + email;
  }

  private boolean isVerified(final byte[] signature, final PublicKey publicKey, final String hash)
      throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {

    final Signature sig = Signature.getInstance("SHA256withRSA");
    sig.initVerify(publicKey);
    sig.update(hash.getBytes());
    return sig.verify(signature);
  }
}
