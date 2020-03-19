package mops.hhu.de.rheinjug1.praxis.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import mops.hhu.de.rheinjug1.praxis.database.entities.SignatureRecord;
import mops.hhu.de.rheinjug1.praxis.database.repositories.EventRepository;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.models.Chart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ChartService {

  @Autowired MeetupService meetupService;
  @Autowired EventRepository eventRepository;
  @Autowired ReceiptService receiptService;
  FormatService formatService;

  public int getNumberOfReceiptsByMeetupType(final MeetupType meetupType) {

    final LinkedList<SignatureRecord> asList = new LinkedList<>();
    receiptService.getAll().forEach(x -> asList.add(x));
    return (int)
        asList.stream()
            .map(x -> getMeetupTypeById(x.getMeetupId()))
            .filter(x -> x.isPresent())
            .map(x -> x.get())
            .filter(x -> x == meetupType)
            .count();
  }

  private Optional<MeetupType> getMeetupTypeById(final long id) {
    final Optional<Event> optionalEvent = eventRepository.findById(id);
    return optionalEvent.isPresent()
        ? Optional.of(optionalEvent.get().getMeetupType())
        : Optional.empty();
  }

  public Chart getXEventsChart(final int events) {
    final List<Event> xEvents = meetupService.getLastXEvents(events);

    final List<String> dates =
        xEvents.stream()
            .map(i -> i.getZonedDateTime())
            .map(i -> formatService.toLocalDateString(i))
            .collect(Collectors.toList());

    final List<Integer> participants =
        xEvents.stream().map(i -> meetupService.getSubmissionCount(i)).collect(Collectors.toList());

    return new Chart(dates, participants);
  }
}
