package mops.hhu.de.rheinjug1.praxis.clients;

import java.util.Arrays;
import java.util.List;
import mops.hhu.de.rheinjug1.praxis.clients.dto.EventResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MeetupClient {

  @Value("${meetup.api.url}")
  private String meetupApiUrl;

  public List<EventResponseDTO> getUpcomingEvents() {
    final RestTemplate restTemplate = new RestTemplate();
    final ResponseEntity<EventResponseDTO[]> response =
        restTemplate.getForEntity(
            meetupApiUrl + "/events?status=upcoming", EventResponseDTO[].class);

    return Arrays.asList(response.getBody());
  }
}
