package mops.hhu.de.rheinjug1.praxis.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

public class FormaterServiceTest {

  TimeFormatService formater = new TimeFormatService();

  @Test
  void formatTimeHours() {
    final Duration testDuration = Duration.ofHours(2);
    final String wantedFormatting = "02:00";

    assertThat(formater.format(testDuration)).isEqualTo(wantedFormatting);
  }

  @Test
  void formatTimeMinutes() {
    final Duration testDuration = Duration.ofMinutes(25);
    final String wantedFormatting = "00:25";

    assertThat(formater.format(testDuration)).isEqualTo(wantedFormatting);
  }

  @Test
  void formatDate() {
    final ZonedDateTime testDate = ZonedDateTime.of(2020, 3, 12, 12, 30, 0, 0, ZoneId.of("UTC"));
    final ZoneId testZone = ZoneId.of("UTC");
    final String wantedFormatting = "2020-03-12 12:30:00";

    assertThat(formater.toLocalEventTimeString(testDate, testZone)).isEqualTo(wantedFormatting);
  }

  @Test
  void formatDateOtherZone() {
    final ZonedDateTime testDate = ZonedDateTime.of(2020, 3, 12, 12, 30, 0, 0, ZoneId.of("UTC"));
    final ZoneId germanZone = ZoneId.of("Europe/Berlin");
    final String wantedFormatting =
        "2020-03-12 13:30:00"; // Winterzeit (bzw. Normalzeit) bis 29.03.2020 +1:00

    assertThat(formater.toLocalEventTimeString(testDate, germanZone)).isEqualTo(wantedFormatting);
  }
}
