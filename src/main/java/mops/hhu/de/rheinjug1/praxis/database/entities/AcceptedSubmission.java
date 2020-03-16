package mops.hhu.de.rheinjug1.praxis.database.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Getter
@NoArgsConstructor
public class AcceptedSubmission {

  @Id private Long id;
  private Long meetupId;
  private String email;
  private String name;
  private String minIoLink;

  public AcceptedSubmission(final Long meetupId, final String name, final String email) {
    this.meetupId = meetupId;
    this.email = email;
    this.name = name;
  }

  public AcceptedSubmission(
      final Long meetupId, final String email, final String name, final String minIoLink) {
    this.meetupId = meetupId;
    this.email = email;
    this.name = name;
    this.minIoLink = minIoLink;
  }
}
