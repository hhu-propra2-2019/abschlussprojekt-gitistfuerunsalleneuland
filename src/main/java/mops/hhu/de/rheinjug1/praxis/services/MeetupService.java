package mops.hhu.de.rheinjug1.praxis.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
  @Autowired private JdbcAggregateTemplate jdbcAggregateTemplate;

  @Scheduled(fixedRate = 3000) // Todo:Zeitintervall?
  private void update() {
    final List<Event> meetupEvents = meetupClient.getAllEvents();
    final List<Event> allEvents = eventRepository.findAll();
    updateExistingEvents(meetupEvents, allEvents);
    insertNonExistingEvents(meetupEvents, allEvents);
  }

  private void updateExistingEvents(final List<Event> meetupEvents, final List<Event> allEvents) {
    meetupEvents.stream()
        .filter(allEvents::contains)
        .forEach(jdbcAggregateTemplate::update); // .forEach(this::update);
  }

  private void insertNonExistingEvents(
      final List<Event> meetupEvents, final List<Event> allEvents) {
    meetupEvents.stream()
        .filter(i -> !allEvents.contains(i))
        .forEach(jdbcAggregateTemplate::insert);
  }

  public List<Event> getEventsByStatus(final String status) {
    switch (status) {
      case "upcoming":
        return filterEventsByStatus("upcoming");
      case "past":
        return filterEventsByStatus("past");
      case "all":
        return eventRepository.findAll();
      default:
        return new ArrayList<>();
    }
  }

  private List<Event> filterEventsByStatus(final String status) {
    return eventRepository.findAll().stream()
        .filter(i -> i.getStatus().equals(status))
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

  public List<Event> getLastXEvents(final int x) {
    final List<Event> pastEvents = getEventsByStatus("past");
    final int n = pastEvents.size();
    if (n < x) {
      return pastEvents;
    }
    return pastEvents.subList(n - x, n);
  }
}
