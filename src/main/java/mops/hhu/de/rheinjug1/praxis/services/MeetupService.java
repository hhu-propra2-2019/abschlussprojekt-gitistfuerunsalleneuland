package mops.hhu.de.rheinjug1.praxis.services;

import java.util.List;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import mops.hhu.de.rheinjug1.praxis.clients.MeetupClient;
import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import mops.hhu.de.rheinjug1.praxis.database.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@SuppressWarnings({"PMD.UnusedPrivateField", "PMD.UnusedPrivateMethod"})
@EnableScheduling
@Component
public class MeetupService {

  @Autowired private MeetupClient meetupClient;
  @Autowired private EventRepository repo;
  @Autowired DataSource dataSource;
  @Autowired JdbcAggregateTemplate template;

  @Scheduled(fixedRate = 3000) // Todo:Zeitintervall?
  private void update() {
    final List<Event> meetupEvents = meetupClient.getAllEvents();
    final List<Event> allEvents = repo.findAll();
    updateExistingEvents(meetupEvents, allEvents);
    insertNonExistingEvents(meetupEvents, allEvents);
  }

  private void updateExistingEvents(final List<Event> meetupEvents, final List<Event> allEvents) {
    meetupEvents.stream().filter(i -> allEvents.contains(i)).forEach(i -> repo.save(i));
  }

  private void insertNonExistingEvents(
      final List<Event> meetupEvents, final List<Event> allEvents) {
    meetupEvents.stream().filter(i -> !allEvents.contains(i)).forEach(i -> template.insert(i));
  }

  public List<Event> getUpcomingEvents() {
    return repo.findAll().stream()
        .filter(i -> i.getStatus().equals("upcoming"))
        .collect(Collectors.toList());
  }

  public List<Event> getAll() {
    return repo.findAll();
  }
}
