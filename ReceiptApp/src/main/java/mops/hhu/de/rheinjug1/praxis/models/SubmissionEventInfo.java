package mops.hhu.de.rheinjug1.praxis.models;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;

@Builder
@ToString
@Getter
public class SubmissionEventInfo {
  private final Long id;
  private final Long meetupId;
  private final String minIoLink;
  private final boolean accepted;
  private final String eventTitle;
  private final String eventLink;
  private final String eventDateTime;
  private final MeetupType meetupType;
  private final String name;
}
