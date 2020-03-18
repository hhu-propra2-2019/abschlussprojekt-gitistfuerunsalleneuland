package mops.hhu.de.rheinjug1.praxis.services;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import mops.hhu.de.rheinjug1.praxis.models.Chart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ChartService {

  @Autowired MeetupService meetUpService;
  FormatService formatService;

  public Chart getXEventsChart(final int events) {
    final List<Event> xEvents = meetUpService.getLastXEvents(events);

    final List<String> dates = xEvents.stream()
            .map(i->i.getZonedDateTime())
            .map(i->formatService.toLocalDateString(i))
            .collect(Collectors.toList());

    final List<Integer> participants = xEvents.stream()
            .map(i->meetUpService.getSubmissionCount(i))
            .collect(Collectors.toList());

    return new Chart(dates, participants);
  }

}
