package mops.hhu.de.rheinjug1.praxis.services;

import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import org.springframework.stereotype.Service;

@Service
public class MeetupService {

  public MeetupType getType(final long meetupId) {
    return MeetupType.ENTWICKELBAR;
  }

  public String getTitle(final long meetupId) {
    return "testMeetup";
  }
}
