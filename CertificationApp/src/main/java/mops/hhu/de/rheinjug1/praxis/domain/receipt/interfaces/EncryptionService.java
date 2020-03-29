package mops.hhu.de.rheinjug1.praxis.domain.receipt.interfaces;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.entities.Receipt;

public interface EncryptionService {
  Receipt sign(Receipt receipt) throws IOException, InvalidKeyException, SignatureException;

  boolean isVerified(Receipt receipt)
      throws IOException, InvalidKeyException, SignatureException, NoSuchAlgorithmException;
}
