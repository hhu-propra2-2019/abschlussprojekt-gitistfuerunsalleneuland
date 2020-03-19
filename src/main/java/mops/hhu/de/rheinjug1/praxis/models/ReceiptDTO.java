package mops.hhu.de.rheinjug1.praxis.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;

@Data
@NoArgsConstructor
public class ReceiptDTO {
    private String name;
    private String email;
    private long meetupId;
    private String title;
    private MeetupType meetupType;
    private String signature;
}
