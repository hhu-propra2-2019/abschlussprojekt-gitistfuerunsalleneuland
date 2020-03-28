package mops.hhu.de.rheinjug1.praxis.adapters.encryption;

import lombok.RequiredArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.Receipt;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.*;


@Service
@RequiredArgsConstructor
public class EncryptionService {
    private final KeyPair keyPair;
    private final Signature securitySignature;

    byte[] sign(final Receipt receipt) throws IOException, InvalidKeyException, SignatureException {
        securitySignature.initSign(keyPair.getPrivate());
        securitySignature.update(receipt.getPlainText());
        return securitySignature.sign();
    }

    boolean isVerified(final Receipt receipt) throws IOException, InvalidKeyException, SignatureException, NoSuchAlgorithmException {
        securitySignature.initVerify(keyPair.getPublic());
        securitySignature.update(receipt.getPlainText());
        return securitySignature.verify(receipt.getSignature());
    }
}
