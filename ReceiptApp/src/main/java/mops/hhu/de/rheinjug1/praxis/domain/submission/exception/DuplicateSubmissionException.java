package mops.hhu.de.rheinjug1.praxis.domain.submission.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "duplicate submission")
public class DuplicateSubmissionException extends Exception {
  public DuplicateSubmissionException(final Long meetupId, final String email) {
    super(
        String.format(
            "Submission with meetup_id %d and email %s already exists!", meetupId, email));
  }
}
