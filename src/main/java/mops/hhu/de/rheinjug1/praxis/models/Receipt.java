package mops.hhu.de.rheinjug1.praxis.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;

@ToString
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Receipt {

  private final String name;
  private final String email;
  private final Long meetupId;
  private final String meetupTitle;
  private final MeetupType meetupType;
  private final String signature;
}
