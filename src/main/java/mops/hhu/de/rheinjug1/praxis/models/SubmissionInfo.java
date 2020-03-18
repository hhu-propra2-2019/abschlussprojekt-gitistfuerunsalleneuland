package mops.hhu.de.rheinjug1.praxis.models;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;

@Builder
@ToString
@Getter
public class SubmissionInfo {
  private Long id;
  private final Long meetupId;
  private final String minIoLink;
  private boolean accepted;
  String eventName;
  String eventLink;
  MeetupType meetupType;
}
