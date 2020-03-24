package mops.hhu.de.rheinjug1.praxis.hex.domain.submission.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UnauthorizedSubmissionAccessException extends Exception {
  public UnauthorizedSubmissionAccessException(final Long submissionId) {
    super(String.format("The requested submission with id %d is from another user", submissionId));
  }
}
