package mops.hhu.de.rheinjug1.praxis.models;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ChartTest {

  List<Talk> talks = new LinkedList<>();
  final LocalDateTime sample = LocalDateTime.of(2020, 3, 12, 12, 30);
  Chart chart;

  @Test
  void testDataStringOneEvent() {
    talks.add(new Talk(sample, 5));

    chart = new Chart(talks);
    assertThat(chart.getData()).isEqualTo("[5]");
  }

  @Test
  void testDataStringMultibleEvents() {
    talks.add(new Talk(sample, 5));
    talks.add(new Talk(sample, 6));

    chart = new Chart(talks);
    assertThat(chart.getData()).isEqualTo("[5,6]");
  }

  @Test
  void testDateStringOneEvent() {
    talks.add(new Talk(sample, 5));

    chart = new Chart(talks);
    assertThat(chart.getDates()).isEqualTo("['12.03.2020']");
  }

  @Test
  void testDateStringMultibleEvents() {
    talks.add(new Talk(sample, 5));
    talks.add(new Talk(sample, 5));

    chart = new Chart(talks);
    assertThat(chart.getDates()).isEqualTo("['12.03.2020','12.03.2020']");
  }
}
