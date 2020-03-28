package mops.hhu.de.rheinjug1.praxis.adapters.encryption;

import java.io.IOException;
import java.security.*;
import lombok.RequiredArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.Receipt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EncryptionService {
  private final KeyPair keyPair;
  private final Signature securitySignature;

  Receipt sign(final Receipt receipt) throws IOException, InvalidKeyException, SignatureException {
    securitySignature.initSign(keyPair.getPrivate());
    securitySignature.update(receipt.getPlainText());
    receipt.setSignature(securitySignature.sign());
    return receipt;
  }

  boolean isVerified(final Receipt receipt)
      throws IOException, InvalidKeyException, SignatureException, NoSuchAlgorithmException {
    securitySignature.initVerify(keyPair.getPublic());
    securitySignature.update(receipt.getPlainText());
    return securitySignature.verify(receipt.getSignature());
  }
}
