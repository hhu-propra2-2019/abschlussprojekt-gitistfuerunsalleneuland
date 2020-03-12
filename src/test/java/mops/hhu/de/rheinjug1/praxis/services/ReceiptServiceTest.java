package mops.hhu.de.rheinjug1.praxis.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import mops.hhu.de.rheinjug1.praxis.entities.AcceptedSubmission;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.models.Receipt;
import mops.hhu.de.rheinjug1.praxis.repositories.ReceiptSignatureRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ReceiptServiceTest {

  @Autowired private ReceiptService receiptService;

  @MockBean private MeetupService meetupService;

  @MockBean private EncryptionService encryptionService;

  @MockBean private ReceiptSignatureRepository receiptSignatureRepository;

  @Test
  public void receiptService_returns_correct_receipt()
      throws CertificateException, InvalidKeyException, NoSuchAlgorithmException, IOException,
          KeyStoreException, SignatureException, UnrecoverableEntryException {
    final Long meetupId = 12_345L;
    final Long keycloakId = 54_321L;
    final String name = "testName";
    final String meetupTitle = "testMeetupTitle";
    final String signature = "testSignature";
    final MeetupType meetupType = MeetupType.ENTWICKELBAR;

    when(meetupService.getType(meetupId)).thenReturn(MeetupType.ENTWICKELBAR);
    when(meetupService.getTitle(meetupId)).thenReturn(meetupTitle);
    when(encryptionService.sign(meetupType, meetupId, keycloakId)).thenReturn(signature);
    when(receiptSignatureRepository.save(any())).thenReturn(null);

    final AcceptedSubmission submission = new AcceptedSubmission(meetupId, keycloakId);
    final Receipt receipt =
        receiptService.createReceiptAndSaveSignatureInDatabase(submission, name);

    final Receipt expectedReceipt = new Receipt(name, meetupId, meetupTitle, meetupType, signature);
    assertThat(expectedReceipt).isEqualTo(receipt);
  }
}
