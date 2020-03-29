package mops.hhu.de.rheinjug1.praxis.database.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import mops.hhu.de.rheinjug1.praxis.domain.submission.Submission;
import org.junit.jupiter.api.Test;

class SubmissionTest {

  @Test
  void cantConstructEmptySubmission() {
    assertThrows(
        NullPointerException.class,
        () -> {
          Submission.builder().build();
        });
  }

  @Test
  void canConstructFilledSubmission() {
    final Submission submission =
        Submission.builder()
            .email("Test@test.de")
            .meetupId(264_559L)
            .minIoLink("minio.com")
            .name("Pommes")
            .accepted(true)
            .build();

    assertThat(submission.getEmail()).isEqualTo("Test@test.de");
    assertThat(submission.isAccepted()).isTrue();
  }

  @Test
  void acceptedIsDefaultFalse() {
    final Submission submission =
        Submission.builder().email("Test@test.de").meetupId(264_559L).name("Pommes").build();

    assertThat(submission.isAccepted()).isFalse();
  }
}
