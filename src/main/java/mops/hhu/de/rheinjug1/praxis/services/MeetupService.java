package mops.hhu.de.rheinjug1.praxis.services;

import mops.hhu.de.rheinjug1.praxis.clients.MeetupClient;
import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@SuppressWarnings("PMD.UnusedPrivateField")
@EnableScheduling
public class MeetupService {

  @Autowired private MeetupClient meetupClient;

  @Scheduled(fixedRate = 300000)  //Todo:Zeitintervall?
  private List<Event> Update() {
    return meetupClient.getAllEvents();
  }


}
