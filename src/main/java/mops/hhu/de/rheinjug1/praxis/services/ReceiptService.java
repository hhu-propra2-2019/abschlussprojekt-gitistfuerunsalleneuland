package mops.hhu.de.rheinjug1.praxis.services;

import io.vavr.control.Option;
import mops.hhu.de.rheinjug1.praxis.entities.AcceptedSubmission;
import mops.hhu.de.rheinjug1.praxis.entities.ReceiptSignature;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.models.Receipt;
import mops.hhu.de.rheinjug1.praxis.repositories.ReceiptSignatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceiptService {

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private MeetupService meetupService;

    @Autowired
    private ReceiptSignatureRepository receiptSignatureRepository;

    public Option<Receipt> createReceiptAndSaveSignatureInDatabase(final AcceptedSubmission submission, final String name) {
        final long meetupId = submission.getMeetupId();
        final MeetupType meetupType = meetupService.getType(meetupId);

        final String meetUpTitle = meetupService.getTitle(meetupId);

        final Option<String> signatureOption = encryptionService.sign(meetupType, submission.getKeycloakId(), meetupId);

        if(signatureOption.isEmpty()) {
            return Option.none();
        }

        final String signature = signatureOption.get();

        final ReceiptSignature receiptSignature = new ReceiptSignature(signature);
        receiptSignatureRepository.save(receiptSignature);
        return Option.of(new Receipt(name, meetupId, meetUpTitle, meetupType, signature));
    }

    ;
}
