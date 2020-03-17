package mops.hhu.de.rheinjug1.praxis.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Optional;
import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import mops.hhu.de.rheinjug1.praxis.database.repositories.EventRepository;
import mops.hhu.de.rheinjug1.praxis.entities.AcceptedSubmission;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.exceptions.EventNotFoundException;
import mops.hhu.de.rheinjug1.praxis.models.Receipt;
import mops.hhu.de.rheinjug1.praxis.repositories.ReceiptSignatureRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ReceiptServiceTest {

  @Autowired private ReceiptService receiptService;

  @MockBean private EncryptionService encryptionService;

  @MockBean private ReceiptSignatureRepository receiptSignatureRepository;

  @MockBean private EventRepository eventRepository;

  private static final Event TEST_EVENT =
      new Event(
          "testDuration",
          0L,
          "testMeetupTitle",
          "testStatus",
          "testZonedDateTime",
          "testLink",
          "testDescription",
          MeetupType.ENTWICKELBAR);

  @Test
  void receiptService_returns_correct_receipt()
      throws CertificateException, InvalidKeyException, NoSuchAlgorithmException, IOException,
          KeyStoreException, SignatureException, UnrecoverableEntryException,
          EventNotFoundException {
    final Long meetupId = 12_345L;
    final Long keycloakId = 54_321L;
    final String name = "testName";
    final String meetupTitle = "testMeetupTitle";
    final String signature = "testSignature";
    final MeetupType meetupType = MeetupType.ENTWICKELBAR;

    when(eventRepository.findById(meetupId)).thenReturn(Optional.of(TEST_EVENT));
    when(encryptionService.sign(meetupType, meetupId, keycloakId)).thenReturn(signature);
    when(receiptSignatureRepository.save(any())).thenReturn(null);

    final AcceptedSubmission submission = new AcceptedSubmission(meetupId, keycloakId);
    final Receipt receipt =
        receiptService.createReceiptAndSaveSignatureInDatabase(submission, name);

    final Receipt expectedReceipt = new Receipt(name, meetupId, meetupTitle, meetupType, signature);
    assertThat(expectedReceipt).isEqualTo(receipt);
  }
}
