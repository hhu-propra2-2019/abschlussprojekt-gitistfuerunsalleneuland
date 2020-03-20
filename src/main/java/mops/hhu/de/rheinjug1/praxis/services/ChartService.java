package mops.hhu.de.rheinjug1.praxis.services;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import mops.hhu.de.rheinjug1.praxis.database.repositories.EventRepository;
import mops.hhu.de.rheinjug1.praxis.database.repositories.SignatureRepository;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.models.Chart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ChartService {

  @Autowired MeetupService meetupService;
  @Autowired EventRepository eventRepository;
  @Autowired SignatureRepository signatureRepository;
  FormatService formatService;

  public int getNumberOfReceiptsByMeetupType(final MeetupType meetupType) {
    return signatureRepository.countSignatureByMeetupType(meetupType.databaseRepresentation());
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
