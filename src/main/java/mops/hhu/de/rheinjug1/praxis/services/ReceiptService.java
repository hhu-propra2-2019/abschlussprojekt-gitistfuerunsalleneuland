package mops.hhu.de.rheinjug1.praxis.services;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Optional;
import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import mops.hhu.de.rheinjug1.praxis.database.repositories.EventRepository;
import mops.hhu.de.rheinjug1.praxis.entities.AcceptedSubmission;
import mops.hhu.de.rheinjug1.praxis.entities.ReceiptSignature;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.exceptions.EventNotFoundException;
import mops.hhu.de.rheinjug1.praxis.models.Receipt;
import mops.hhu.de.rheinjug1.praxis.repositories.ReceiptSignatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceiptService {

  @Autowired private EncryptionService encryptionService;

  @Autowired private ReceiptSignatureRepository receiptSignatureRepository;

  @Autowired private EventRepository eventRepository;

  public Receipt createReceiptAndSaveSignatureInDatabase(
      final AcceptedSubmission submission, final String name)
      throws UnrecoverableEntryException, NoSuchAlgorithmException, IOException,
          CertificateException, KeyStoreException, SignatureException, InvalidKeyException,
          EventNotFoundException {
    final Long meetupId = submission.getMeetupId();

    final Optional<Event> eventOptional = eventRepository.findById(meetupId);

    if (!eventOptional.isPresent()) {
      throw new EventNotFoundException(meetupId);
    }

    final Event event = eventOptional.get();

    final MeetupType meetupType = event.getMeetupType();
    final String meetUpTitle = event.getName();

    final String signature =
        encryptionService.sign(meetupType, meetupId, submission.getKeycloakId());

    final ReceiptSignature receiptSignature = new ReceiptSignature(signature, meetupId);
    receiptSignatureRepository.save(receiptSignature);
    return new Receipt(name, meetupId, meetUpTitle, meetupType, signature);
  };
}
