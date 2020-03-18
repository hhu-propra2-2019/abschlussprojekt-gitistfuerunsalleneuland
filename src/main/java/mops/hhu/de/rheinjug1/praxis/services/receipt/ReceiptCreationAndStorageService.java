package mops.hhu.de.rheinjug1.praxis.services.receipt;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import mops.hhu.de.rheinjug1.praxis.database.entities.SignatureRecord;
import mops.hhu.de.rheinjug1.praxis.database.entities.Submission;
import mops.hhu.de.rheinjug1.praxis.database.repositories.SignatureRepository;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.exceptions.EventNotFoundException;
import mops.hhu.de.rheinjug1.praxis.models.Receipt;
import mops.hhu.de.rheinjug1.praxis.services.EncryptionService;
import mops.hhu.de.rheinjug1.praxis.services.MeetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.stereotype.Service;

@Service
public class ReceiptCreationAndStorageService {

  private final EncryptionService encryptionService;

  private final SignatureRepository signatureRepository;

  private final JdbcAggregateTemplate jdbcAggregateTemplate;

  private final MeetupService meetupService;

  @Autowired
  public ReceiptCreationAndStorageService(
      final EncryptionService encryptionService,
      final SignatureRepository signatureRepository,
      final JdbcAggregateTemplate jdbcAggregateTemplate,
      final MeetupService meetupService) {
    this.encryptionService = encryptionService;
    this.signatureRepository = signatureRepository;
    this.jdbcAggregateTemplate = jdbcAggregateTemplate;
    this.meetupService = meetupService;
  }

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
    return new Receipt(
        studentName, studentEmail, meetUpId, meetUpTitle, meetupType, signatureString);
  };
}
