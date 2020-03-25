package mops.hhu.de.rheinjug1.praxis.domain.chart;

import static java.util.stream.Collectors.toList;

import java.util.List;
import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.domain.TimeFormatService;
import mops.hhu.de.rheinjug1.praxis.domain.event.Event;
import mops.hhu.de.rheinjug1.praxis.domain.event.MeetupService;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.SignatureRepository;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ChartService {

  private final MeetupService meetupService;
  private final SignatureRepository signatureRepository;
  private final TimeFormatService timeFormatService;

  public int getNumberOfReceiptsByMeetupType(final MeetupType meetupType) {
    return signatureRepository.countSignatureByMeetupType(meetupType.databaseRepresentation());
  }

  public Chart getXEventsChart(final int events) {
    final List<Event> xEvents = meetupService.getLastXEvents(events);

    final List<String> dates =
        xEvents.stream().map(timeFormatService::getGermanDateString).collect(toList());

    final List<Integer> participants =
        xEvents.stream().map(meetupService::getSubmissionCount).collect(toList());

    return new Chart(dates, participants);
  }
}
