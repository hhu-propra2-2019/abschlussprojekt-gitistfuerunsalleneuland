package mops.hhu.de.rheinjug1.praxis.clients;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import mops.hhu.de.rheinjug1.praxis.clients.dto.EventResponseDTO;
import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MeetupClient {

  @Value("${meetup.api.url}")
  private String meetupApiUrl;

  private final RestTemplate restTemplate = new RestTemplate();

  public List<Event> getAllEvents() {
    final ResponseEntity<EventResponseDTO[]> response =
        restTemplate.getForEntity(meetupApiUrl + "/events?status=upcoming,past", EventResponseDTO[].class);
    List<EventResponseDTO> AllEvents = Arrays.stream(response.getBody()).collect(Collectors.toList());
    return AllEvents.stream().map(i->i.toEvent()).collect(Collectors.toList());
  }
}
