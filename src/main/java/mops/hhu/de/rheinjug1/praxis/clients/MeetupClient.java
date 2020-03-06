package mops.hhu.de.rheinjug1.praxis.clients;

import java.util.Arrays;
import java.util.List;
import mops.hhu.de.rheinjug1.praxis.clients.entities.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MeetupClient {

  public List<Event> getUpcomingEvents() {
    final RestTemplate restTemplate = new RestTemplate();
    final String meetupResourceUrl = "https://api.meetup.com/rheinjug/events?status=upcoming,past";
    final ResponseEntity<Event[]> response =
        restTemplate.getForEntity(meetupResourceUrl, Event[].class);
    return Arrays.asList(response.getBody());
  }
}
