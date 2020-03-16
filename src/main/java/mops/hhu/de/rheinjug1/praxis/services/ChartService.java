package mops.hhu.de.rheinjug1.praxis.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import mops.hhu.de.rheinjug1.praxis.models.Chart;
import mops.hhu.de.rheinjug1.praxis.models.Talk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ChartService {

  @Autowired MeetupService meetUpService;

  public Chart getXEventsChart(final int events) {
    final List<Event> xEvents = meetUpService.getLastXEvents(events);
    final List<Talk> xTalks = xEvents.stream().map(x -> toTalk(x)).collect(Collectors.toList());
    return new Chart(xTalks);
  }

  private Talk toTalk(final Event event) {
    final DateTimeFormatter formatter =
        DateTimeFormatter.ofPattern(FormatService.getDateTimePattern());
    final LocalDateTime time = LocalDateTime.parse(event.getZonedDateTime(), formatter);
    return new Talk(time, (int) (Math.random() * 20)); // actual Data naccesary
  }
}
