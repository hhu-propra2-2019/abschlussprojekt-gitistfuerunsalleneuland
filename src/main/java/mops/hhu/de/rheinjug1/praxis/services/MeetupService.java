package mops.hhu.de.rheinjug1.praxis.services;

import mops.hhu.de.rheinjug1.praxis.clients.MeetupClient;
import org.springframework.beans.factory.annotation.Autowired;

public class MeetupService {

  @Autowired private MeetupClient meetupClient;
}
