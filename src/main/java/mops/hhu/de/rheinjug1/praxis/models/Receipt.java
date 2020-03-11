package mops.hhu.de.rheinjug1.praxis.models;

import lombok.AllArgsConstructor;
import lombok.ToString;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;

@AllArgsConstructor
@ToString
public class Receipt {

  private final String name;
  private final long meetupId;
  private final String meetupTitle;
  private final MeetupType meetupType;
  private final String signature;
}