package mops.hhu.de.rheinjug1.praxis.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import mops.hhu.de.rheinjug1.praxis.database.repositories.SignatureRepository;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.models.Chart;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ChartService {

  private final MeetupService meetupService;
  private final SignatureRepository signatureRepository;
  private final TimeFormatService timeFormatService;
  private static final int DEFAULT_NUMBER_DATAPOINTS = 6;

  public int getNumberOfReceiptsByMeetupType(final MeetupType meetupType) {
    return signatureRepository.countSignatureByMeetupType(meetupType.databaseRepresentation());
  }

  public Chart getXEventsChart(final int events) {
    final List<Event> xEvents = meetupService.getLastXEvents(events);

    final List<String> dates =
        xEvents.stream()
            .map(i -> timeFormatService.getGermanDateString(i))
            .collect(Collectors.toList());

    final List<Integer> participants =
        xEvents.stream().map(i -> meetupService.getSubmissionCount(i)).collect(Collectors.toList());

    return new Chart(dates, participants);
  }

  public Chart getXEventsChart(final Optional<String> datapoints) {
    final int numberDatapoints = getNumberDatapoints(datapoints);
    if (numberDatapoints <= 0) {
      return getXEventsChart(meetupService.getNumberPastEvents());
    }
    return getXEventsChart(numberDatapoints);
  }

  private int getNumberDatapoints(final Optional<String> datapoints) {
    if (datapoints == null) {
      return DEFAULT_NUMBER_DATAPOINTS;
    }
    if (datapoints.isPresent()) {
      try {
        return Integer.parseInt(datapoints.get());
      } catch (NumberFormatException e) {
        return DEFAULT_NUMBER_DATAPOINTS;
      }
    }
    return DEFAULT_NUMBER_DATAPOINTS;
  }
}
