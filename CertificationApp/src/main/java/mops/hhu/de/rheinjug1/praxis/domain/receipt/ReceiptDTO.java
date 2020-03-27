package mops.hhu.de.rheinjug1.praxis.domain.receipt;

import java.io.Serializable;

import lombok.*;
import mops.hhu.de.rheinjug1.praxis.domain.ByteString;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDTO implements Serializable {

  private String name;
  private String email;
  private Long meetupId;
  private String meetupTitle;
  private MeetupType meetupType;
  private ByteString plainText;
  private ByteString signature;

  @Builder
  public ReceiptDTO(String name, String email, Long meetupId, String meetupTitle, MeetupType meetupType) {
    this.name = name;
    this.email = email;
    this.meetupId = meetupId;
    this.meetupTitle = meetupTitle;
    this.meetupType = meetupType;
    this.plainText = 
  }

  @SuppressWarnings("PMD")
  @Override
  public ReceiptDTO clone() {
    return new ReceiptDTO(name, email, meetupId, meetupTitle, meetupType, signature);
  }
}
