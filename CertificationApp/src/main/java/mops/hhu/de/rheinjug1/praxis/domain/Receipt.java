package mops.hhu.de.rheinjug1.praxis.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.MeetupType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Receipt {

  private String name;
  private String email;
  private long meetupId;
  private String meetupTitle;
  private MeetupType meetupType;
  private String signature;

  public Receipt cloneThisReceipt() {
    return new Receipt(name, email, meetupId, meetupTitle, meetupType, signature);
  }
}
