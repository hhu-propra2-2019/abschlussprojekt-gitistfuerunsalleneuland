package mops.hhu.de.rheinjug1.praxis.database.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Getter
@NoArgsConstructor
public class Submission {

  @Id private Long id;
  private Long meetupId;
  private String email;
  private String name;
  private String minIoLink;
  private boolean accepted = false;

  public Submission(final Long meetupId, final String name, final String email) {
    this.meetupId = meetupId;
    this.email = email;
    this.name = name;
  }

  public Submission(
      final Long meetupId, final String email, final String name, final String minIoLink) {
    this.meetupId = meetupId;
    this.email = email;
    this.name = name;
    this.minIoLink = minIoLink;
  }

  public Submission(
      final Long meetupId,
      final String email,
      final String name,
      final String minIoLink,
      final boolean accepted) {
    this.meetupId = meetupId;
    this.email = email;
    this.name = name;
    this.minIoLink = minIoLink;
    this.accepted = accepted;
  }
}
