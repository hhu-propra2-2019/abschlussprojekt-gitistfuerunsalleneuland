package mops.hhu.de.rheinjug1.praxis.domain.submission;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import mops.hhu.de.rheinjug1.praxis.domain.Account;
import mops.hhu.de.rheinjug1.praxis.domain.TimeFormatService;
import org.joda.time.LocalDateTime;

import java.util.Optional;

@Getter
@EqualsAndHashCode
public class Submission {

  private Long id;
  @NonNull private final Long meetupId;
  @NonNull private final String email;
  @NonNull private final String name;
  @NonNull private final String minIoLink;
  private boolean accepted = false;
  private String acceptanceDateTime;
  @NonNull private TimeFormatService timeFormatService;

  @Builder
  public Submission(
          final Long meetupId,
          final String email,
          final String name,
          final String minIoLink,
          final boolean accepted,
          final String acceptanceDateTime,
          @NonNull TimeFormatService timeFormatService) {
    this.meetupId = Optional.ofNullable(meetupId).orElse(0L);
    this.email = Optional.ofNullable(email).orElse("");
    this.name = Optional.ofNullable(name).orElse("");
    this.minIoLink = Optional.ofNullable(minIoLink).orElse("");
    this.accepted = accepted;
    this.acceptanceDateTime = Optional.ofNullable(acceptanceDateTime).orElse("");
    this.timeFormatService = timeFormatService;
  }

  public boolean isFromUser(final Account account) {
    return name.equals(account.getName()) && email.equals(account.getEmail());
  }

  public void accept() {
    this.accepted = true;
    this.acceptanceDateTime =
        LocalDateTime.now().toString(timeFormatService.getDatabaseDateTimePattern());
  }
}
