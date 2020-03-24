package mops.hhu.de.rheinjug1.praxis.domain.submission;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import mops.hhu.de.rheinjug1.praxis.annotations.DB;
import mops.hhu.de.rheinjug1.praxis.domain.Account;
import mops.hhu.de.rheinjug1.praxis.domain.TimeFormatService;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;

import java.util.Optional;

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
  private String acceptanceDateTime;
  private TimeFormatService timeFormatService;

  @Builder
  public Submission(
      @NonNull final Long meetupId,
      @NonNull final String email,
      @NonNull final String name,
      final String minIoLink,
      final boolean accepted,
      final String acceptanceDateTime,
      @Autowired TimeFormatService timeFormatService) {
    this.meetupId = meetupId;
    this.email = email;
    this.name = name;
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
