package mops.hhu.de.rheinjug1.praxis.adapters.encryption;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.List;
import mops.hhu.de.rheinjug1.praxis.domain.certification.DuplicateSignatureException;
import mops.hhu.de.rheinjug1.praxis.domain.certification.SignatureDoesntMatchException;
import mops.hhu.de.rheinjug1.praxis.domain.certification.VerificationService;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.Receipt;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.ReceiptEntity;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.ReceiptRepository;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationServiceImpl implements VerificationService {

  private final KeyService keyService;
  private final ReceiptRepository receiptRepository;

  @Autowired
  public VerificationServiceImpl(
      final KeyService keyService, final ReceiptRepository receiptRepository) {
    this.keyService = keyService;
    this.receiptRepository = receiptRepository;
  }

  @Override
  public boolean isSignatureValid(final Receipt receipt)
      throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
          UnrecoverableEntryException, IOException, InvalidKeyException, SignatureException,
          DuplicateSignatureException, SignatureDoesntMatchException {

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
      throw new SignatureDoesntMatchException();
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
