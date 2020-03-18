package mops.hhu.de.rheinjug1.praxis.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.clients.MeetupClient;
import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import mops.hhu.de.rheinjug1.praxis.database.repositories.EventRepository;
import mops.hhu.de.rheinjug1.praxis.exceptions.EventNotFoundException;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@SuppressWarnings({"PMD.UnusedPrivateField", "PMD.UnusedPrivateMethod"})
@EnableScheduling
@Component
@AllArgsConstructor
public class MeetupService {

  private final MeetupClient meetupClient;
  private final EventRepository eventRepository;
  final JdbcAggregateTemplate jdbcAggregateTemplate;

  public List<Event> getAllEvents() {
    return eventRepository.findAll();
  }

  @Scheduled(fixedDelay = 3_600_000) // Todo:Zeitintervall?
  private void update() {
    final List<Event> meetupEvents = meetupClient.getAllEvents();
    final List<Event> allEvents = eventRepository.findAll();
    updateExistingEvents(meetupEvents, allEvents);
    insertNonExistingEvents(meetupEvents, allEvents);
  }

  private void updateExistingEvents(final List<Event> meetupEvents, final List<Event> allEvents) {
    meetupEvents.stream().filter(allEvents::contains).forEach(eventRepository::save);
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

  public Event getEventIfExistent(final Long meetUpId) throws EventNotFoundException {
    final Optional<Event> eventOptional = eventRepository.findById(meetUpId);

    if (eventOptional.isEmpty()) {
      throw new EventNotFoundException(meetUpId);
    }

    return eventOptional.get();
  }
}
