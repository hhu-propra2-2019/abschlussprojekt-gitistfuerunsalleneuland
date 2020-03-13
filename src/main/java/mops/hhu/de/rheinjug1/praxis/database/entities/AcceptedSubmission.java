package mops.hhu.de.rheinjug1.praxis.database.entities;

import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public class AcceptedSubmission {

  @Id private Long id;
  private Long meetupId;
  private String email;
  private String name;
  private String minIoLink;

  public AcceptedSubmission(final Long meetupId, final String email, final String name) {
    this.meetupId = meetupId;
    this.email = email;
    this.name = name;
  }
}
