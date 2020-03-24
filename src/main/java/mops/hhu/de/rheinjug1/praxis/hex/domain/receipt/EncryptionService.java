package mops.hhu.de.rheinjug1.praxis.hex.domain.receipt;

import mops.hhu.de.rheinjug1.praxis.hex.enums.MeetupType;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

public interface EncryptionService {
    String sign(MeetupType meetupType, long meetupId, String name, String email)
        throws NoSuchAlgorithmException, IOException, InvalidKeyException, KeyStoreException,
            UnrecoverableEntryException, CertificateException, SignatureException;
}
