package mops.hhu.de.rheinjug1.praxis.database.entities;

import java.util.Optional;
import lombok.*;
import mops.hhu.de.rheinjug1.praxis.models.Account;
import mops.hhu.de.rheinjug1.praxis.services.TimeFormatService;
import org.joda.time.LocalDateTime;
import org.springframework.data.annotation.Id;

@Getter
@EqualsAndHashCode
public class Submission {

  @Id private Long id;
  @NonNull private final Long meetupId;
  @NonNull private final String email;
  @NonNull private final String name;
  @NonNull private final String minIoLink;
  private boolean accepted = false;
  private String acceptanceDateTime;

  @Builder
  public Submission(
      @NonNull final Long meetupId,
      @NonNull final String email,
      @NonNull final String name,
      final String minIoLink,
      final boolean accepted,
      final String acceptanceDateTime) {
    this.meetupId = meetupId;
    this.email = email;
    this.name = name;
    this.minIoLink = Optional.ofNullable(minIoLink).orElse("");
    this.accepted = accepted;
    this.acceptanceDateTime = Optional.ofNullable(acceptanceDateTime).orElse("");
  }

  public boolean isFromUser(final Account account) {
    return name.equals(account.getName()) && email.equals(account.getEmail());
  }

  public void accept() {
    this.accepted = true;
    this.acceptanceDateTime =
        LocalDateTime.now().toString(TimeFormatService.DATABASE_DATE_TIME_PATTERN);
  }
}
