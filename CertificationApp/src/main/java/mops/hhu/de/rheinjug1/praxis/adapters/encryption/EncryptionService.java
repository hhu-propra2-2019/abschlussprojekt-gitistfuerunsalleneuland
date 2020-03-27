package mops.hhu.de.rheinjug1.praxis.adapters.encryption;

import lombok.RequiredArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.domain.ByteString;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.*;


@Service
@RequiredArgsConstructor
public class EncryptionService {
    private final KeyPair keyPair;
    private final Signature securitySignature;


    boolean isVerified(final ByteString signature, final ByteString originalString) throws IOException, InvalidKeyException, SignatureException, NoSuchAlgorithmException {
        securitySignature.initVerify(keyPair.getPublic());
        securitySignature.update(originalString.getBytes());
        return securitySignature.verify(signature.getBytes());
    }
}
