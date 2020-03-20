package mops.hhu.de.rheinjug1.praxis.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Optional;
import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import mops.hhu.de.rheinjug1.praxis.database.entities.Submission;
import mops.hhu.de.rheinjug1.praxis.database.repositories.EventRepository;
import mops.hhu.de.rheinjug1.praxis.database.repositories.SignatureRepository;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.exceptions.EventNotFoundException;
import mops.hhu.de.rheinjug1.praxis.models.Receipt;
import mops.hhu.de.rheinjug1.praxis.services.receipt.ReceiptCreationAndStorageService;
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
    final String signature = "testSignature";
    final MeetupType meetupType = MeetupType.ENTWICKELBAR;

    when(eventRepository.findById(meetupId)).thenReturn(Optional.of(TEST_EVENT));
    when(encryptionService.sign(meetupType, meetupId, name, email)).thenReturn(signature);
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
            .signature(signature)
            .meetupType(meetupType)
            .build();
    assertThat(receipt).isEqualTo(expectedReceipt);
  }
}
