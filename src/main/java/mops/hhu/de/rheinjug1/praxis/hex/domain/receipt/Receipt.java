package mops.hhu.de.rheinjug1.praxis.hex.domain.receipt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.hex.enums.MeetupType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Receipt {

  private String name;
  private String email;
  private Long meetupId;
  private String meetupTitle;
  private MeetupType meetupType;
  private String signature;
}
