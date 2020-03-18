package mops.hhu.de.rheinjug1.praxis.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import mops.hhu.de.rheinjug1.praxis.database.entities.SignatureRecord;
import mops.hhu.de.rheinjug1.praxis.database.repositories.EventRepository;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.exceptions.EventNotFoundException;
import mops.hhu.de.rheinjug1.praxis.models.Chart;
import mops.hhu.de.rheinjug1.praxis.models.Talk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ChartService {

  @Autowired MeetupService meetupService;
  @Autowired EventRepository eventRepository;
  @Autowired ReceiptService receiptService;

  public Chart getXEventsChart(final int events) {
    final List<Event> xEvents = meetupService.getLastXEvents(events);
    final List<Talk> xTalks = xEvents.stream().map(x -> toTalk(x)).collect(Collectors.toList());
    return new Chart(xTalks);
  }

  private Talk toTalk(final Event event) {
    final DateTimeFormatter formatter =
        DateTimeFormatter.ofPattern(FormatService.getDateTimePattern());
    final LocalDateTime time = LocalDateTime.parse(event.getZonedDateTime(), formatter);
    return new Talk(time, meetupService.getSubmissionCount(event));
  }

  public Map<Long, Integer> filterByMeetUpType(
      final Map<Long, Integer> allReceipts, final MeetupType meetupType) {
    return allReceipts.entrySet().stream()
        .filter(
            x -> {
              try {
                return getMeetupTypeById(x.getKey()) == meetupType;
              } catch (EventNotFoundException e) {
                return false;
              }
            })
        .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));
  }

  public int getNumberOfEntwickelbarReceipts() {
    final Map<Long, Integer> entwickelbarEvents =
        filterByMeetUpType(toMapWithoutDoubles(receiptService.getAll()), MeetupType.ENTWICKELBAR);
    return entwickelbarEvents.entrySet().stream().mapToInt(x -> x.getValue()).sum();
  }

  public int getNumberOfRheinjugReceipts() {
    final Map<Long, Integer> entwickelbarEvents =
        filterByMeetUpType(toMapWithoutDoubles(receiptService.getAll()), MeetupType.RHEINJUG);
    return entwickelbarEvents.entrySet().stream().mapToInt(x -> x.getValue()).sum();
  }

  private MeetupType getMeetupTypeById(final long id) throws EventNotFoundException {
    final Optional<Event> optional = eventRepository.findById(id);
    if (optional.isEmpty()) {
      throw new EventNotFoundException(id);
    }
    return optional.get().getMeetupType();
  }

  private Map<Long, Integer> toMapWithoutDoubles(final Iterable<SignatureRecord> withDoubles) {
    final HashMap<Long, Integer> noDoubles = new HashMap<>();
    for (final SignatureRecord current : withDoubles) {
      if (noDoubles.containsKey(current.getMeetupId())) {
        noDoubles.put(current.getMeetupId(), 1);
      } else {
        final int number = noDoubles.get(current.getMeetupId()) + 1;
        noDoubles.replace(current.getMeetupId(), number);
      }
    }
    return noDoubles;
  }
}
