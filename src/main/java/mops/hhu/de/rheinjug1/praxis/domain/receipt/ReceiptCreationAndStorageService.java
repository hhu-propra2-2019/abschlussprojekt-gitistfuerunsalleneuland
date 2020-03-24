package mops.hhu.de.rheinjug1.praxis.domain.receipt;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.domain.event.Event;
import mops.hhu.de.rheinjug1.praxis.domain.event.EventNotFoundException;
import mops.hhu.de.rheinjug1.praxis.domain.event.MeetupService;
import mops.hhu.de.rheinjug1.praxis.domain.submission.Submission;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
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
