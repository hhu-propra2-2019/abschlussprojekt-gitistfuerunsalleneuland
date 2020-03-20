package mops.hhu.de.rheinjug1.praxis.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.LinkedList;
import java.util.List;
import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import mops.hhu.de.rheinjug1.praxis.database.repositories.EventRepository;
import mops.hhu.de.rheinjug1.praxis.database.repositories.SignatureRepository;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChartServiceTest {

  private final List<Event> sampleData = new LinkedList<>();
  private MeetupService meetupServiceMock;
  private ChartService chartService;

  @BeforeEach
  void init() {
    final String time = "12:30 - 12.03.2020";
    sampleData.add(
        Event.builder().id(1).zonedDateTime(time).meetupType(MeetupType.RHEINJUG).build());
    sampleData.add(
        Event.builder().id(2).zonedDateTime(time).meetupType(MeetupType.RHEINJUG).build());
    sampleData.add(
        Event.builder().id(3).zonedDateTime(time).meetupType(MeetupType.ENTWICKELBAR).build());
    this.meetupServiceMock = mock(MeetupService.class);
    final EventRepository eventRepository = spy(EventRepository.class);
    final SignatureRepository signatureRepository = mock(SignatureRepository.class);
    this.chartService =
        new ChartService(
            meetupServiceMock, eventRepository, signatureRepository, new TimeFormatService());
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
