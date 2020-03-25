package mops.hhu.de.rheinjug1.praxis.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import mops.hhu.de.rheinjug1.praxis.database.repositories.SignatureRepository;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ChartServiceTest {

  private final List<Event> sampleData = new LinkedList<>();
  private MeetupService meetupServiceMock;
  private ChartService chartService;
  private int defaultNumerDatapoints;

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
    final SignatureRepository signatureRepository = mock(SignatureRepository.class);
    this.chartService =
        new ChartService(meetupServiceMock, signatureRepository, new TimeFormatService());
    this.defaultNumerDatapoints = chartService.getDefaultNumberDatapoints();
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

  @Test
  void testGetXEventsChartOptional() {
    when(meetupServiceMock.getLastXEvents(2)).thenReturn(sampleData.subList(1, 3));
    when(meetupServiceMock.getNumberPastEvents()).thenReturn(3);

    final int numberOfTalks =
        chartService.getXEventsChart(Optional.of(String.valueOf(2))).getTalksLength();

    verify(meetupServiceMock).getLastXEvents(2);
    assertThat(numberOfTalks).isEqualTo(2);
  }

  @Test
  void testGetXEventsChartBigInt() {
    when(meetupServiceMock.getLastXEvents(Mockito.anyInt())).thenReturn(sampleData);
    when(meetupServiceMock.getNumberPastEvents()).thenReturn(3);

    final int numberOfTalks =
        chartService.getXEventsChart(Optional.of(String.valueOf(5))).getTalksLength();

    assertThat(numberOfTalks).isEqualTo(3);
  }

  @Test
  void testGetXEventsChartNullOptional() {
    when(meetupServiceMock.getLastXEvents(Mockito.anyInt())).thenReturn(sampleData);
    when(meetupServiceMock.getNumberPastEvents()).thenReturn(3);

    chartService.getXEventsChart(null);

    verify(meetupServiceMock).getLastXEvents(defaultNumerDatapoints);
  }

  @Test
  void testGetXEventsChartEmptyOptionl() {
    when(meetupServiceMock.getLastXEvents(Mockito.anyInt())).thenReturn(sampleData);
    when(meetupServiceMock.getNumberPastEvents()).thenReturn(3);

    chartService.getXEventsChart(Optional.empty());

    verify(meetupServiceMock).getLastXEvents(defaultNumerDatapoints);
  }

  @Test
  void testGetXEventsChartNegativOptional() {
    when(meetupServiceMock.getLastXEvents(Mockito.anyInt())).thenReturn(sampleData);
    when(meetupServiceMock.getNumberPastEvents()).thenReturn(3);

    chartService.getXEventsChart(Optional.of(String.valueOf(-1)));

    verify(meetupServiceMock, times(1)).getNumberPastEvents();
    verify(meetupServiceMock).getLastXEvents(3);
  }
}
