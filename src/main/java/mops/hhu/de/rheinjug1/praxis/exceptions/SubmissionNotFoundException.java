package mops.hhu.de.rheinjug1.praxis.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "submission not found")
public class SubmissionNotFoundException extends Exception {
  public SubmissionNotFoundException(final Long submissionId) {
    super(String.format("Submission with the id %d could not be found in database", submissionId));
  }
}
