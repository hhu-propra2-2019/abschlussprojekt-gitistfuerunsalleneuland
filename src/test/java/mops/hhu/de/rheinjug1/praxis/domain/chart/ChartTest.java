package mops.hhu.de.rheinjug1.praxis.domain.chart;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import mops.hhu.de.rheinjug1.praxis.adapters.time.TimeFormatServiceImpl;
import mops.hhu.de.rheinjug1.praxis.domain.chart.ChartData.ChartDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChartTest {

  List<Integer> participants = new LinkedList<>();
  List<String> dates = new LinkedList<>();
  List<ChartData> data = new LinkedList<>();
  Chart chart;
  static final String SAMPLE = "12.03.2020";
  static final String JSON_STRING =
      '"' + SAMPLE
          + '"'; // JSON_STRING = "12.03.2020" , the doublequotes are important for JSON format

  @BeforeEach
  void setUp() {
    participants.clear();
    dates.clear();
    data.clear();
  }

  @Test
  void testDataStringOneEvent() {
    // Arrange
    participants.add(5);
    dates.add(SAMPLE);
    data = createData(dates, participants);
    chart = new Chart(data);
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
    data = createData(dates, participants);
    chart = new Chart(data);
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
    List<ChartData> data = data = createData(dates, participants);
    chart = new Chart(data);
    // Act
    final String dataString = chart.getParticipants();
    // Assert
    assertThat(dataString).isEqualTo("[5]");
  }

  @Test
  void testDateStringMultibleEvents() {
    // Arrange
    dates.add(SAMPLE);
    dates.add(SAMPLE);
    participants.add(5);
    participants.add(5);
    data = createData(dates, participants);
    chart = new Chart(data);
    // Act
    final String dates = chart.getDates();
    // Assert
    assertThat(dates).isEqualTo("[" + JSON_STRING + "," + JSON_STRING + "]");
  }

  private List<ChartData> createData(final List<String> dates, final List<Integer> participants) {
    return IntStream.range(0, dates.size())
        .mapToObj(i -> chartDataBuild(dates.get(i), participants.get(i)))
        .collect(Collectors.toList());
  }

  private ChartData chartDataBuild(final String date, final Integer participants) {
    return new ChartDataBuilder(new TimeFormatServiceImpl())
        .date(date)
        .submissions(participants)
        .build();
  }
}
