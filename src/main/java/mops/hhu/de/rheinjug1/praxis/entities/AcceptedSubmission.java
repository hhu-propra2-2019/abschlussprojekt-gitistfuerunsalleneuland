package mops.hhu.de.rheinjug1.praxis.entities;

import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public class AcceptedSubmission {

  @Id private Long id;
  private Long meetupId;
  private Long keycloakId;
  private String minIoLink;

  public AcceptedSubmission(final Long meetupId, final Long keycloakId) {
    this.meetupId = meetupId;
    this.keycloakId = keycloakId;
  }
}
