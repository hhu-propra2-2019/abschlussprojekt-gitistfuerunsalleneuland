package mops.hhu.de.rheinjug1.praxis.adapters.encryption;

import mops.hhu.de.rheinjug1.praxis.domain.receipt.Receipt;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.ReceiptDTO;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import org.bouncycastle.util.encoders.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.security.*;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class EncryptionServiceTest {
    @Autowired private EncryptionService encryptionService;

    @Test
    void encryptedReceiptIsValid() throws InvalidKeyException, IOException, SignatureException, NoSuchAlgorithmException {
        final Receipt receipt = Receipt.createFromDTO(new ReceiptDTO(
                "name",
                "email",
                (long) 1,
                "meetupTitle",
                MeetupType.ENTWICKELBAR,
                null));

        final byte[] signature = encryptionService.sign(receipt);
        receipt.setSignature(signature);
        assertTrue(encryptionService.isVerified(receipt));
    }
}