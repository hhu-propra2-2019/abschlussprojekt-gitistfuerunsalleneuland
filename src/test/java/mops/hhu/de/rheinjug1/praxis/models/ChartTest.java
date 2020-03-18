package mops.hhu.de.rheinjug1.praxis.models;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import mops.hhu.de.rheinjug1.praxis.services.FormatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChartTest {

  List<Integer> participants = new LinkedList<>();
  List<String> dates = new LinkedList<>();
  final String sample = "12.03.2020";
  Chart chart;

  @BeforeEach
  void setup(){
    participants.clear();
    dates.clear();
  }

  @Test
  void testDataStringOneEvent() {
    //Arrange
    participants.add(5);
    dates.add(sample);
    chart = new Chart(dates,participants);
    //Act
    String data = chart.getData();
    //Assert
    assertThat(data).isEqualTo("[5]");
  }

  @Test
  void testDataStringMultibleEvents() {
    //Arrange
    participants.add(5);
    participants.add(6);
    dates.add(sample);
    dates.add(sample);
    chart = new Chart(dates,participants);
    //Act
    String data = chart.getData();
    //Assert
    assertThat(data).isEqualTo("[5,6]");
  }

  @Test
  void testDateStringOneEvent() {
    //Arrange
    dates.add(sample);
    participants.add(5);
    chart = new Chart(dates,participants);
    //Act
    String data = chart.getData();
    //Assert
    assertThat(data).isEqualTo("[" + '"' + "12.03.2020" + '"' + "]");
  }

  @Test
  void testDateStringMultibleEvents() {
    //Arrange
    dates.add(sample);
    dates.add(sample);
    participants.add(5);
    participants.add(5);
    chart = new Chart(dates,participants);
    //Act
    String dates = chart.getDates();
    //Assert
    assertThat(dates).isEqualTo("[" +'"' + "12.03.2020" +'"' +"," + '"'+"12.03.2020"+'"' + "]");
  }
}
