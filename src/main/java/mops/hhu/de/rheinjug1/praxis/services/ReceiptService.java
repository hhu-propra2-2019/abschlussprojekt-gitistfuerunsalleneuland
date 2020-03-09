package mops.hhu.de.rheinjug1.praxis.services;

import mops.hhu.de.rheinjug1.praxis.entities.AcceptedSubmission;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.models.Receipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceiptService {

  @Autowired EncryptionService encryptionService;

  @Autowired MeetupService meetupService;

  public Receipt createReceipt(final AcceptedSubmission submission, final String name) {
    final long meetupId = submission.getMeetupId();
    final String meetUpTitle = meetupService.getTitle(meetupId);
    final MeetupType meetupType = meetupService.getType(meetupId);

    final String hash = encryptionService.hash(submission.getKeycloakId(), meetupId, meetupType);

    final String signature = encryptionService.sign(hash);

    return new Receipt();
  };
}
