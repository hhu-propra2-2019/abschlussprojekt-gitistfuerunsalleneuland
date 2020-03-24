package mops.hhu.de.rheinjug1.praxis.adapters.meetup;

import java.util.Arrays;
import java.util.List;

import mops.hhu.de.rheinjug1.praxis.adapters.meetup.dto.EventResponseDTO;
import mops.hhu.de.rheinjug1.praxis.domain.event.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static java.util.stream.Collectors.*;

@Component
public class MeetupClient {

  @Value("${meetup.api.url}")
  private String meetupApiUrl;

  private final RestTemplate restTemplate = new RestTemplate();

  public List<Event> getAllEvents() {
    final ResponseEntity<EventResponseDTO[]> response =
        restTemplate.getForEntity(
            meetupApiUrl + "/events?status=upcoming,past", EventResponseDTO[].class);
    final List<EventResponseDTO> allEvents =
        Arrays.stream(response.getBody()).collect(toList());
    return allEvents.stream().map(EventResponseDTO::toEvent).collect(toList());
  }
}
