package mops.hhu.de.rheinjug1.praxis.adapters.encryption;

import java.io.IOException;
import java.security.*;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.domain.certification.DuplicateSignatureException;
import mops.hhu.de.rheinjug1.praxis.domain.certification.SignatureDoesntMatchException;
import mops.hhu.de.rheinjug1.praxis.domain.certification.VerificationService;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.Receipt;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.ReceiptEntity;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.ReceiptRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

  private final KeyPair keyPair;
  private final ReceiptRepository receiptRepository;
  private final EncryptionService encryptionService;

  @Override
  public boolean isSignatureValid(final Receipt receipt)
      throws NoSuchAlgorithmException, IOException, InvalidKeyException, SignatureException,
          DuplicateSignatureException, SignatureDoesntMatchException {

    final List<ReceiptEntity> identicalSignatures =
        receiptRepository.findReceiptEntityBySignature(receipt.getSignature());
    if (!identicalSignatures.isEmpty()) {
      throw new DuplicateSignatureException();
    }
    if (!encryptionService.isVerified(receipt)) {
      throw new SignatureDoesntMatchException();
    }
    return true;
  }
}
