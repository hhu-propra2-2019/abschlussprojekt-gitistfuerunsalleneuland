package mops.hhu.de.rheinjug1.praxis.services;

import java.util.List;
import mops.hhu.de.rheinjug1.praxis.clients.MeetupClient;
import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SuppressWarnings({"PMD.UnusedPrivateField", "PMD.UnusedPrivateMethod"})
@EnableScheduling
public class MeetupService {

  @Autowired private MeetupClient meetupClient;

  @Scheduled(fixedRate = 300_000) // Todo:Zeitintervall?
  private List<Event> update() {
    return meetupClient.getAllEvents();
  }
}
