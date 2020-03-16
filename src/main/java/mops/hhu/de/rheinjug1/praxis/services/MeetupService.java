package mops.hhu.de.rheinjug1.praxis.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

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
  @Autowired private EventRepository eventRepository;
  @Autowired JdbcAggregateTemplate jdbcAggregateTemplate;
  
  @PostConstruct
  private void initDatabase() {
	  update();
  }

  @Scheduled(cron = "0 0 8 * * ?") // Todo:Zeitintervall?
  private void update() {
    final List<Event> meetupEvents = meetupClient.getAllEvents();
    final List<Event> allEvents = eventRepository.findAll();
    updateExistingEvents(meetupEvents, allEvents);
    insertNonExistingEvents(meetupEvents, allEvents);
  }

  private void updateExistingEvents(final List<Event> meetupEvents, final List<Event> allEvents) {
    meetupEvents.stream()
        .filter(allEvents::contains)
        .forEach(this::updateWithoutParticipantsCounter);
  }

  private void insertNonExistingEvents(
      final List<Event> meetupEvents, final List<Event> allEvents) {
    meetupEvents.stream()
        .filter(i -> !allEvents.contains(i))
        .forEach(jdbcAggregateTemplate::insert);
  }

  public List<Event> getUpcomingEvents() {
    return eventRepository.findAll().stream()
        .filter(i -> i.getStatus().equals("upcoming"))
        .collect(Collectors.toList());
  }

  private void updateWithoutParticipantsCounter(final Event e) {
    eventRepository.updateWithoutParticipantsCounter(
        e.getId(),
        e.getDuration(),
        e.getName(),
        e.getStatus(),
        e.getZonedDateTime(),
        e.getLink(),
        e.getDescription());
  }

  public List<Event> getAll() {
    return eventRepository.findAll();
  }

  public List<Event> getLastXEvents(final int x) {
    final long totalEvents =
        eventRepository.findAll().stream().filter(y -> y.getStatus().contentEquals("past")).count();
    if (totalEvents < x) {
      return eventRepository.findAll().stream()
          .filter(y -> y.getStatus().contentEquals("past"))
          .collect(Collectors.toList());
    }
    return eventRepository.findAll().stream()
        .filter(y -> y.getStatus().contentEquals("past"))
        .skip(totalEvents - x)
        .collect(Collectors.toList());
  }
}
