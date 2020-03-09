package mops.hhu.de.rheinjug1.praxis.entities;

import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public class AcceptedSubmission {

  @Id private Long id;
  private Long meetupId;
  private Long keycloakId;
  private String minIoLink;
}
