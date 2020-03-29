package mops.hhu.de.rheinjug1.praxis.adapters.encryption;

import java.io.IOException;
import java.security.*;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.domain.certification.DuplicateSignatureException;
import mops.hhu.de.rheinjug1.praxis.domain.certification.SignatureDoesntMatchException;
import mops.hhu.de.rheinjug1.praxis.domain.certification.VerificationService;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.entities.Receipt;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.entities.SignatureRecord;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.interfaces.EncryptionService;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.interfaces.SignatureRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

  private final SignatureRepository signatureRepository;
  private final EncryptionService encryptionService;

  @Override
  public boolean isSignatureValid(final Receipt receipt)
      throws NoSuchAlgorithmException, IOException, InvalidKeyException, SignatureException,
          DuplicateSignatureException, SignatureDoesntMatchException {

    final List<SignatureRecord> identicalSignatures =
        signatureRepository.findReceiptEntityBySignature(receipt.getSignature());
    if (!identicalSignatures.isEmpty()) {
      throw new DuplicateSignatureException();
    }
    if (!encryptionService.isVerified(receipt)) {
      throw new SignatureDoesntMatchException();
    }
    return true;
  }
}
