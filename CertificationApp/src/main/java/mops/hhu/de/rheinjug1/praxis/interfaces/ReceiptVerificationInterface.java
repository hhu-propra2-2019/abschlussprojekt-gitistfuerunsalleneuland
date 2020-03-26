package mops.hhu.de.rheinjug1.praxis.interfaces;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import mops.hhu.de.rheinjug1.praxis.domain.Receipt;
import mops.hhu.de.rheinjug1.praxis.exceptions.DuplicateSignatureException;
import mops.hhu.de.rheinjug1.praxis.exceptions.SignatureDoesntMatchEsception;

public interface ReceiptVerificationInterface {

  boolean isSignatureValid(Receipt receipt)
      throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
          UnrecoverableEntryException, IOException, InvalidKeyException, SignatureException, DuplicateSignatureException, SignatureDoesntMatchEsception;
}
