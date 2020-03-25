package mops.hhu.de.rheinjug1.praxis.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.clients.MeetupClient;
import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import mops.hhu.de.rheinjug1.praxis.database.repositories.EventRepository;
import mops.hhu.de.rheinjug1.praxis.database.repositories.SubmissionEventInfoRepository;
import mops.hhu.de.rheinjug1.praxis.database.repositories.SubmissionRepository;
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
  private final SubmissionEventInfoRepository submissionEventInfoRepository;
  final SubmissionRepository submissionRepository;

  @PostConstruct
  private void initDatabase() {
    update();
  }

  @Scheduled(cron = "0 0 8 * * ?")
  public void update() {
    final List<Event> meetupEvents = meetupClient.getAllEventsIfAvailable();
    final List<Event> allEvents = eventRepository.findAll();
    updateExistingEvents(meetupEvents, allEvents);
    insertNonExistingEvents(meetupEvents, allEvents);
  }

  private void updateExistingEvents(final List<Event> meetupEvents, final List<Event> allEvents) {
    meetupEvents.stream().filter(allEvents::contains).forEach(eventRepository::save);
  }

  public int getSubmissionCount(final Event event) {
    return submissionRepository.countSubmissionByMeetupId(event.getId());
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

  public List<Event> getLastXEvents(final int x) {
    final List<Event> pastEvents = getEventsByStatus("past");
    final int n = pastEvents.size();
    if (n < x) {
      return pastEvents;
    }
    return pastEvents.subList(n - x, n);
  }

  public Event getEventIfExistent(final Long meetUpId) throws EventNotFoundException {
    final Optional<Event> eventOptional = eventRepository.findById(meetUpId);

    if (eventOptional.isEmpty()) {
      throw new EventNotFoundException(meetUpId);
    }

    return eventOptional.get();
  }
}
