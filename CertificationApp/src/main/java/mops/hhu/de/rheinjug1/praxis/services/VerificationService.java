package mops.hhu.de.rheinjug1.praxis.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.List;
import mops.hhu.de.rheinjug1.praxis.domain.Receipt;
import mops.hhu.de.rheinjug1.praxis.entities.ReceiptEntity;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.exceptions.DuplicateSignatureException;
import mops.hhu.de.rheinjug1.praxis.exceptions.SignatureDoesntMatchExeption;
import mops.hhu.de.rheinjug1.praxis.interfaces.ReceiptVerificationInterface;
import mops.hhu.de.rheinjug1.praxis.repositories.ReceiptRepository;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationService implements ReceiptVerificationInterface {

  private final KeyService keyService;
  private final ReceiptRepository receiptRepository;

  @Autowired
  public VerificationService(
      final KeyService keyService, final ReceiptRepository receiptRepository) {
    this.keyService = keyService;
    this.receiptRepository = receiptRepository;
  }

  @Override
  public boolean isSignatureValid(final Receipt receipt)
      throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
          UnrecoverableEntryException, IOException, InvalidKeyException, SignatureException,
          DuplicateSignatureException, SignatureDoesntMatchExeption {

    final PublicKey publicKey = keyService.getKeyPairFromKeyStore().getPublic();

    final String name = receipt.getName();
    final long meetupId = receipt.getMeetupId();
    final MeetupType meetupType = receipt.getMeetupType();
    final String email = receipt.getEmail();
    final byte[] signature = Base64.decode(receipt.getSignature());
    final String hashValue = createHashValue(meetupType, meetupId, name, email);

    final List<ReceiptEntity> identicalSignatures =
        receiptRepository.findReceiptEntityBySignature(signature.toString());
    if (!identicalSignatures.isEmpty()) {
      throw new DuplicateSignatureException();
    }
    if (!isVerified(signature, publicKey, hashValue)) {
      throw new SignatureDoesntMatchExeption();
    }

    return true;
  }

  private String createHashValue(
      final MeetupType meetupType, final long meetupId, final String name, final String email) {
    return meetupType.getLabel() + meetupId + name + email;
  }

  private boolean isVerified(
      final byte[] signaturetoVerify, final PublicKey publicKey, final String hashValue)
      throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {

    final Signature sign = Signature.getInstance("SHA256withRSA");
    sign.initVerify(publicKey);
    final byte[] hashValueBytes = hashValue.getBytes(StandardCharsets.UTF_8);
    sign.update(hashValueBytes);
    return sign.verify(signaturetoVerify);
  }
}
