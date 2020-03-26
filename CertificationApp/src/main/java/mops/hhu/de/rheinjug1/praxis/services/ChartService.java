package mops.hhu.de.rheinjug1.praxis.services;

import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.repositories.ReceiptRepository;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ChartService {

    private final ReceiptRepository receiptRepository;

    public int getNumberOfReceiptsByMeetupType(final MeetupType meetupType) {
        int count = receiptRepository.countSignatureByMeetupType(meetupType.getLabel().toUpperCase());
        if(meetupType.getLabel().toUpperCase().equals("RHEINJUG")){
            return count / 3;
        }
        return count;
    }
}
