package mops.hhu.de.rheinjug1.praxis.models;

import lombok.*;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;

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
