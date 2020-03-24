package mops.hhu.de.rheinjug1.praxis.hex.domain.receipt;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.hex.domain.event.Event;
import mops.hhu.de.rheinjug1.praxis.hex.domain.receipt.SignatureRecord;
import mops.hhu.de.rheinjug1.praxis.hex.domain.submission.Submission;
import mops.hhu.de.rheinjug1.praxis.hex.domain.receipt.SignatureRepository;
import mops.hhu.de.rheinjug1.praxis.hex.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.hex.domain.event.EventNotFoundException;
import mops.hhu.de.rheinjug1.praxis.hex.domain.receipt.Receipt;
import mops.hhu.de.rheinjug1.praxis.hex.domain.receipt.EncryptionService;
import mops.hhu.de.rheinjug1.praxis.services.MeetupService;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReceiptCreationAndStorageService {

  private final EncryptionService encryptionService;
  private final SignatureRepository signatureRepository;
  private final JdbcAggregateTemplate jdbcAggregateTemplate;
  private final MeetupService meetupService;

  public Receipt createReceiptAndSaveSignatureInDatabase(final Submission submission)
      throws UnrecoverableEntryException, NoSuchAlgorithmException, IOException,
          CertificateException, KeyStoreException, InvalidKeyException, EventNotFoundException,
          SignatureException {
    final Long meetUpId = submission.getMeetupId();

    final Event event = meetupService.getEventIfExistent(meetUpId);

    final MeetupType meetupType = event.getMeetupType();
    final String meetUpTitle = event.getName();
    final String studentName = submission.getName();
    final String studentEmail = submission.getEmail();

    final String signatureString =
        encryptionService.sign(meetupType, meetUpId, studentName, studentEmail);

    final SignatureRecord signature = new SignatureRecord(signatureString, meetUpId);

    try {
      jdbcAggregateTemplate.insert(signature);
    } catch (final DbActionExecutionException e) {
      signatureRepository.save(signature);
    }
    return Receipt.builder()
        .name(studentName)
        .email(studentEmail)
        .meetupId(meetUpId)
        .meetupTitle(meetUpTitle)
        .meetupType(meetupType)
        .signature(signatureString)
        .build();
  }
}
