package mops.hhu.de.rheinjug1.praxis.domain.chart;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChartTest {

  List<Integer> participants = new LinkedList<>();
  List<String> dates = new LinkedList<>();
  static final String SAMPLE = "12.03.2020";
  Chart chart;
  static final String JSON_STRING =
      '"' + SAMPLE
          + '"'; // JSON_STRING = "12.03.2020" , the doublequotes are important for JSON format

  @BeforeEach
  void setUp() {
    participants.clear();
    dates.clear();
  }

  @Test
  void testDataStringOneEvent() {
    // Arrange
    participants.add(5);
    dates.add(SAMPLE);
    chart = new Chart(dates, participants);
    // Act
    final String data = chart.getParticipants();
    // Assert
    assertThat(data).isEqualTo("[5]");
  }

  @Test
  void testDataStringMultibleEvents() {
    // Arrange
    participants.add(5);
    participants.add(6);
    dates.add(SAMPLE);
    dates.add(SAMPLE);
    chart = new Chart(dates, participants);
    // Act
    final String data = chart.getParticipants();
    // Assert
    assertThat(data).isEqualTo("[5,6]");
  }

  @Test
  void testDateStringOneEvent() {
    // Arrange
    dates.add(SAMPLE);
    participants.add(5);
    chart = new Chart(dates, participants);
    // Act
    final String data = chart.getParticipants();
    // Assert
    assertThat(data).isEqualTo("[5]");
  }

  @Test
  void testDateStringMultibleEvents() {
    // Arrange
    dates.add(SAMPLE);
    dates.add(SAMPLE);
    participants.add(5);
    participants.add(5);
    chart = new Chart(dates, participants);
    // Act
    final String dates = chart.getDates();
    // Assert
    assertThat(dates).isEqualTo("[" + JSON_STRING + "," + JSON_STRING + "]");
  }
}
