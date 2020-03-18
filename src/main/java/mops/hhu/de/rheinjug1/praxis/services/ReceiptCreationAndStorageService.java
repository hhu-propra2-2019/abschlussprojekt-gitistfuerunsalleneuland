package mops.hhu.de.rheinjug1.praxis.services;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Optional;

import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import mops.hhu.de.rheinjug1.praxis.database.entities.SignatureRecord;
import mops.hhu.de.rheinjug1.praxis.database.entities.Submission;
import mops.hhu.de.rheinjug1.praxis.database.repositories.EventRepository;
import mops.hhu.de.rheinjug1.praxis.database.repositories.SignatureRepository;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.exceptions.EventNotFoundException;
import mops.hhu.de.rheinjug1.praxis.models.Receipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReceiptCreationAndStorageService {

  private final EncryptionService encryptionService;
  private final SignatureRepository signatureRepository;
  private final EventRepository eventRepository;
  private final JdbcAggregateTemplate jdbcAggregateTemplate;

  public Receipt createReceiptAndSaveSignatureInDatabase(final Submission submission)
      throws UnrecoverableEntryException, NoSuchAlgorithmException, IOException,
          CertificateException, KeyStoreException, SignatureException, InvalidKeyException,
          EventNotFoundException {
    final Long meetUpId = submission.getMeetupId();

    final Optional<Event> eventOptional = eventRepository.findById(meetUpId);

    if (eventOptional.isEmpty()) {
      throw new EventNotFoundException(meetUpId);
    }

    final Event event = eventOptional.get();

    final MeetupType meetupType = event.getMeetupType();
    final String meetUpTitle = event.getName();
    final String studentName = submission.getName();
    final String studentEmail = submission.getEmail();

    final String signatureString =
        encryptionService.sign(meetupType, meetUpId, studentName, studentEmail);

    final SignatureRecord signature = new SignatureRecord(signatureString, meetUpId);
    try {
      saveNew(signature);
    } catch (final DbActionExecutionException e) {
      signatureRepository.save(signature);
    }
    return new Receipt(
        studentName, studentEmail, meetUpId, meetUpTitle, meetupType, signatureString);
  };

  private void saveNew(final SignatureRecord signature) {
    jdbcAggregateTemplate.insert(signature);
  }
}
