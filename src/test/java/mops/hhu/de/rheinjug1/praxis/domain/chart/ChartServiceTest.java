package mops.hhu.de.rheinjug1.praxis.domain.chart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import mops.hhu.de.rheinjug1.praxis.adapters.time.TimeFormatServiceImpl;
import mops.hhu.de.rheinjug1.praxis.domain.event.Event;
import mops.hhu.de.rheinjug1.praxis.domain.event.MeetupService;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.SignatureRepository;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChartServiceTest {

  private final List<Event> sampleData = new LinkedList<>();
  private MeetupService meetupServiceMock;
  private ChartService chartService;

  @BeforeEach
  void init() {
    final String time = "2020-03-12 12:30:00";
    sampleData.add(
        Event.builder().id(1).zonedDateTime(time).meetupType(MeetupType.RHEINJUG).build());
    sampleData.add(
        Event.builder().id(2).zonedDateTime(time).meetupType(MeetupType.RHEINJUG).build());
    sampleData.add(
        Event.builder().id(3).zonedDateTime(time).meetupType(MeetupType.ENTWICKELBAR).build());
    this.meetupServiceMock = mock(MeetupService.class);
    final SignatureRepository signatureRepository = mock(SignatureRepository.class);
    this.chartService =
<<<<<<< HEAD:src/test/java/mops/hhu/de/rheinjug1/praxis/domain/chart/ChartServiceTest.java
        new ChartService(meetupServiceMock, signatureRepository, new TimeFormatServiceImpl());
=======
        new ChartService(meetupServiceMock, signatureRepository, new TimeFormatService());
>>>>>>> master:src/test/java/mops/hhu/de/rheinjug1/praxis/services/ChartServiceTest.java
  }

  @Test
  void testNumberOfDataPoints() {
    // Arrange
    when(meetupServiceMock.getLastXEvents(anyInt())).thenReturn(sampleData);
    // Act
    final int numberOfTalks = chartService.getXEventsChart(2).getTalksLength();
    // Assert
    assertThat(numberOfTalks).isEqualTo(sampleData.size());
  }
}
