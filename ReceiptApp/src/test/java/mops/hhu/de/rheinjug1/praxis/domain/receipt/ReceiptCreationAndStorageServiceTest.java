package mops.hhu.de.rheinjug1.praxis.domain.receipt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Optional;
import mops.hhu.de.rheinjug1.praxis.domain.event.Event;
import mops.hhu.de.rheinjug1.praxis.domain.event.EventNotFoundException;
import mops.hhu.de.rheinjug1.praxis.domain.event.EventRepository;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.entities.Receipt;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.interfaces.EncryptionService;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.interfaces.SignatureRepository;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.services.ReceiptCreationAndStorageService;
import mops.hhu.de.rheinjug1.praxis.domain.submission.Submission;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ReceiptCreationAndStorageServiceTest {

  @Autowired private ReceiptCreationAndStorageService receiptCreationAndStorageService;
  @MockBean private EncryptionService encryptionService;
  @MockBean private SignatureRepository signatureRepository;
  @MockBean private EventRepository eventRepository;

  private static final Event TEST_EVENT =
      Event.builder()
          .id(0L)
          .duration("testDuration")
          .name("testMeetupTitle")
          .status("testStatus")
          .zonedDateTime("testZonedDateTime")
          .link("testLink")
          .description("testDescription")
          .meetupType(MeetupType.ENTWICKELBAR)
          .build();

  @Test
  void it_returns_correct_receipt_in_signature_creation_process()
      throws CertificateException, InvalidKeyException, NoSuchAlgorithmException, IOException,
          KeyStoreException, SignatureException, UnrecoverableEntryException,
          EventNotFoundException {
    final Long meetupId = 12_345L;
    final String name = "testName";
    final String email = "testEmail";
    final String meetupTitle = "testMeetupTitle";
    final MeetupType meetupType = MeetupType.ENTWICKELBAR;

    when(eventRepository.findById(meetupId)).thenReturn(Optional.of(TEST_EVENT));
    when(signatureRepository.save(any())).thenReturn(null);

    final Submission submission =
        Submission.builder().email(email).meetupId(meetupId).name(name).build();
    final Receipt receipt =
        receiptCreationAndStorageService.createReceiptAndSaveSignatureInDatabase(submission);

    final Receipt expectedReceipt =
        Receipt.builder()
            .meetupId(meetupId)
            .name(name)
            .email(email)
            .meetupTitle(meetupTitle)
            .meetupType(meetupType)
            .build();
    encryptionService.sign(receipt);
    assertThat(receipt).isEqualTo(expectedReceipt);
  }
}
