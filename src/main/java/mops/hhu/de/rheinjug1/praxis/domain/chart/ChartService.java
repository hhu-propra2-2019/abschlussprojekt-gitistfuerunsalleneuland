package mops.hhu.de.rheinjug1.praxis.domain.chart;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.domain.TimeFormatService;
import mops.hhu.de.rheinjug1.praxis.domain.event.Event;
import mops.hhu.de.rheinjug1.praxis.domain.event.MeetupService;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.SignatureRepository;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
<<<<<<< HEAD:src/main/java/mops/hhu/de/rheinjug1/praxis/domain/chart/ChartService.java
import org.springframework.beans.factory.annotation.Autowired;
=======
import mops.hhu.de.rheinjug1.praxis.models.Chart;
>>>>>>> master:src/main/java/mops/hhu/de/rheinjug1/praxis/services/ChartService.java
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ChartService {

<<<<<<< HEAD:src/main/java/mops/hhu/de/rheinjug1/praxis/domain/chart/ChartService.java
  @Autowired MeetupService meetupService;
  @Autowired SignatureRepository signatureRepository;
  TimeFormatService formatService;
=======
  private final MeetupService meetupService;
  private final SignatureRepository signatureRepository;
  private final TimeFormatService timeFormatService;
>>>>>>> master:src/main/java/mops/hhu/de/rheinjug1/praxis/services/ChartService.java

  public int getNumberOfReceiptsByMeetupType(final MeetupType meetupType) {
    return signatureRepository.countSignatureByMeetupType(meetupType.databaseRepresentation());
  }

  public Chart getXEventsChart(final int events) {
    final List<Event> xEvents = meetupService.getLastXEvents(events);

    final List<String> dates =
        xEvents.stream()
<<<<<<< HEAD:src/main/java/mops/hhu/de/rheinjug1/praxis/domain/chart/ChartService.java
            .map(i -> formatService.getGermanDateString(i))
=======
            .map(i -> timeFormatService.getGermanDateString(i))
>>>>>>> master:src/main/java/mops/hhu/de/rheinjug1/praxis/services/ChartService.java
            .collect(Collectors.toList());

    final List<Integer> participants =
        xEvents.stream().map(i -> meetupService.getSubmissionCount(i)).collect(Collectors.toList());

    return new Chart(dates, participants);
  }
}
