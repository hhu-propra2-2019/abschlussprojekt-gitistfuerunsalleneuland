package mops.hhu.de.rheinjug1.praxis.hex.domain.chart;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.hex.domain.event.Event;
import mops.hhu.de.rheinjug1.praxis.hex.domain.receipt.SignatureRepository;
import mops.hhu.de.rheinjug1.praxis.hex.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.hex.domain.chart.Chart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ChartService {

  @Autowired
  mops.hhu.de.rheinjug1.praxis.services.MeetupService meetupService;
  @Autowired SignatureRepository signatureRepository;
  mops.hhu.de.rheinjug1.praxis.services.TimeFormatService formatService;

  public int getNumberOfReceiptsByMeetupType(final MeetupType meetupType) {
    return signatureRepository.countSignatureByMeetupType(meetupType.databaseRepresentation());
  }

  public Chart getXEventsChart(final int events) {
    final List<Event> xEvents = meetupService.getLastXEvents(events);

    final List<String> dates =
        xEvents.stream()
            .map(i -> formatService.getGermanDateString(i))
            .collect(Collectors.toList());

    final List<Integer> participants =
        xEvents.stream().map(i -> meetupService.getSubmissionCount(i)).collect(Collectors.toList());

    return new Chart(dates, participants);
  }
}
