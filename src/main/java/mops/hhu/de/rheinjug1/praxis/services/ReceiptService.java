package mops.hhu.de.rheinjug1.praxis.services;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import mops.hhu.de.rheinjug1.praxis.entities.AcceptedSubmission;
import mops.hhu.de.rheinjug1.praxis.entities.ReceiptSignature;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.models.Receipt;
import mops.hhu.de.rheinjug1.praxis.repositories.ReceiptSignatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceiptService {

  @Autowired private EncryptionService encryptionService;

  @Autowired private MeetupService meetupService;

  @Autowired private ReceiptSignatureRepository receiptSignatureRepository;

  public Receipt createReceiptAndSaveSignatureInDatabase(
      final AcceptedSubmission submission, final String name)
      throws UnrecoverableEntryException, NoSuchAlgorithmException, IOException,
          CertificateException, KeyStoreException, SignatureException, InvalidKeyException {
    final long meetupId = submission.getMeetupId();
    final MeetupType meetupType = meetupService.getType(meetupId);

    final String meetUpTitle = meetupService.getTitle(meetupId);

    final String signature =
        encryptionService.sign(meetupType, meetupId, submission.getKeycloakId());

    final ReceiptSignature receiptSignature = new ReceiptSignature(signature, meetupId);
    receiptSignatureRepository.save(receiptSignature);
    return new Receipt(name, meetupId, meetUpTitle, meetupType, signature);
  };
}
