package mops.hhu.de.rheinjug1.praxis.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ChartServiceTest {

  private final List<Event> sampleData = new LinkedList<>();
  private MeetupService meetupServiceMock;
  private ChartService chartService;

  @BeforeEach
  void init() {
    final String time = "12:30 - 12.03.2020";
    sampleData.add(new Event("", 0, "", "", time, "", "", null));
    sampleData.add(new Event("", 0, "", "", time, "", "", null));
    this.meetupServiceMock = mock(MeetupService.class);
    this.chartService = new ChartService(meetupServiceMock, new FormatService());
  }

  @Test
  void testNumberOfDataPoints() {
    // Arrange
    when(meetupServiceMock.getLastXEvents(Mockito.anyInt())).thenReturn(sampleData);
    // Act
    final int numberOfTalks = chartService.getXEventsChart(2).getTalksLength();
    // Assert
    assertThat(numberOfTalks).isEqualTo(sampleData.size());
  }
}
