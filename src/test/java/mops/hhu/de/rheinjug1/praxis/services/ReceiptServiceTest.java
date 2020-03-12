package mops.hhu.de.rheinjug1.praxis.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import mops.hhu.de.rheinjug1.praxis.entities.AcceptedSubmission;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.models.Receipt;
import mops.hhu.de.rheinjug1.praxis.repositories.ReceiptSignatureRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ReceiptServiceTest {

  @Autowired private ReceiptService receiptService;

  @MockBean private MeetupService meetupService;

  @MockBean private EncryptionService encryptionService;

  @MockBean private ReceiptSignatureRepository receiptSignatureRepository;

  @Test
  public void receiptService_returns_correct_receipt()
      throws CertificateException, InvalidKeyException, NoSuchAlgorithmException, IOException,
          KeyStoreException, SignatureException, UnrecoverableEntryException {
    final Long MEETUP_ID = 12345L;
    final Long KEYCLOAK_ID = 54321L;
    final String NAME = "testName";
    final String MEETUP_TITLE = "testMeetupTitle";
    final String SIGNATURE = "testSignature";
    final MeetupType MEETUP_TYPE = MeetupType.ENTWICKELBAR;

    when(meetupService.getType(MEETUP_ID)).thenReturn(MeetupType.ENTWICKELBAR);
    when(meetupService.getTitle(MEETUP_ID)).thenReturn(MEETUP_TITLE);
    when(encryptionService.sign(MEETUP_TYPE, MEETUP_ID, KEYCLOAK_ID)).thenReturn(SIGNATURE);
    when(receiptSignatureRepository.save(any())).thenReturn(null);

    final AcceptedSubmission submission = new AcceptedSubmission(MEETUP_ID, KEYCLOAK_ID);
    final Receipt receipt =
        receiptService.createReceiptAndSaveSignatureInDatabase(submission, NAME);

    final Receipt EXPECTED_RECEIPT =
        new Receipt(NAME, MEETUP_ID, MEETUP_TITLE, MEETUP_TYPE, SIGNATURE);
    assertEquals(EXPECTED_RECEIPT, receipt);
  }
}
