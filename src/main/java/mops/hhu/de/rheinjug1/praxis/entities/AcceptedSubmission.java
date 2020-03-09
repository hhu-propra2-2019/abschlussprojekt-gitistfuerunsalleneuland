package mops.hhu.de.rheinjug1.praxis.entities;

import org.springframework.data.annotation.Id;

public class AcceptedSubmission {

  @Id private Long id;
  private Long meetupId;
  private Long keycloakId;
  private String minIoLink;
}
