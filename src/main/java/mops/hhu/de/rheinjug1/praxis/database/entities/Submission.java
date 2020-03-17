package mops.hhu.de.rheinjug1.praxis.database.entities;

import java.util.Optional;
import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
public class Submission {

  @Id private Long id;
  @NonNull private Long meetupId;
  @NonNull private String email;
  @NonNull private String name;
  @NonNull private String minIoLink;
  private boolean accepted = false;

  @Builder
  public Submission(
      @NonNull final Long meetupId,
      @NonNull final String email,
      @NonNull final String name,
      final String minIoLink,
      final boolean accepted) {
    this.meetupId = meetupId;
    this.email = email;
    this.name = name;
    this.minIoLink = Optional.ofNullable(minIoLink).orElse("");
    this.accepted = accepted;
  }
}
