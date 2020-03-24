package mops.hhu.de.rheinjug1.praxis.adapters.meetup;

import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.List;
import mops.hhu.de.rheinjug1.praxis.domain.event.Event;
import mops.hhu.de.rheinjug1.praxis.domain.event.EventFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MeetupClient {

  @Value("${meetup.api.url}")
  private String meetupApiUrl;

  private final RestTemplate restTemplate = new RestTemplate();
  private final EventFactory eventFactory;

  @Autowired
  public MeetupClient(final EventFactory eventFactory) {
    this.eventFactory = eventFactory;
  }

  public List<Event> getAllEvents() {
    final ResponseEntity<EventMeetupDTO[]> response =
        restTemplate.getForEntity(
            meetupApiUrl + "/events?status=upcoming,past", EventMeetupDTO[].class);
    final List<EventMeetupDTO> allEvents = Arrays.stream(response.getBody()).collect(toList());
    return allEvents.stream().map(eventFactory::createFromDTO).collect(toList());
  }
}
