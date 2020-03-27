package mops.hhu.de.rheinjug1.praxis.domain.submission;

import static mops.hhu.de.rheinjug1.praxis.domain.submission.Submission.builder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class SubmissionTest {

  @Test
  void cantConstructEmptySubmission() {
    assertThrows(
        NullPointerException.class,
        () -> {
          builder().build();
        });
  }

  @Test
  void canConstructFilledSubmission() {
    final Submission submission =
        builder()
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
        builder().email("Test@test.de").meetupId(264_559L).name("Pommes").build();

    assertThat(submission.isAccepted()).isFalse();
  }
}
