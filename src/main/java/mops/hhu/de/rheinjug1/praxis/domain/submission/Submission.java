package mops.hhu.de.rheinjug1.praxis.domain.submission;

import java.util.Optional;
import lombok.*;
import mops.hhu.de.rheinjug1.praxis.annotations.DB;
import org.springframework.data.annotation.Id;

@DB
@Getter
@EqualsAndHashCode
public class Submission {

  @Id private Long id;
  @NonNull private final Long meetupId;
  @NonNull private final String email;
  @NonNull private final String name;
  @NonNull private final String minIoLink;
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

  public void accept() {
    this.accepted = true;
  }
}
