package mops.hhu.de.rheinjug1.praxis.domain.receipt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiptDTO {

    private String name;
    private String email;
    private Long meetupId;
    private String meetupTitle;
    private MeetupType meetupType;
    private byte[] signature;
}