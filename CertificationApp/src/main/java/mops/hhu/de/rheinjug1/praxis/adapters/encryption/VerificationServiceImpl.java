package mops.hhu.de.rheinjug1.praxis.adapters.encryption;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.List;

import lombok.RequiredArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.domain.certification.DuplicateSignatureException;
import mops.hhu.de.rheinjug1.praxis.domain.certification.SignatureDoesntMatchException;
import mops.hhu.de.rheinjug1.praxis.domain.certification.VerificationService;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.ReceiptDTO;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.ReceiptEntity;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.ReceiptRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

  private final KeyConfiguration keyService;
  private final ReceiptRepository receiptRepository;
  private final ByteStringImpl stringBytesConverter;

  @Override
  public boolean isSignatureValid(final ReceiptDTO receipt)
      throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
          UnrecoverableEntryException, IOException, InvalidKeyException, SignatureException,
          DuplicateSignatureException, SignatureDoesntMatchException {

    final PublicKey publicKey = keyService.getKeyPairFromKeyStore().getPublic();

    final byte[] signature = stringBytesConverter.toBytes(receipt.getSignature());
    final String originalString = receipt.toString();

    final List<ReceiptEntity> identicalSignatures =
        receiptRepository.findReceiptEntityBySignature(receipt.getSignature());
    if (!identicalSignatures.isEmpty()) {
      throw new DuplicateSignatureException();
    }
    if (!isVerified(signature, publicKey, originalString)) {
      throw new SignatureDoesntMatchException();
    }
    return true;
  }
}
