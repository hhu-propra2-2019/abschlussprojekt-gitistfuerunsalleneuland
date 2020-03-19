package mops.hhu.de.rheinjug1.praxis.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import mops.hhu.de.rheinjug1.praxis.database.entities.SignatureRecord;
import mops.hhu.de.rheinjug1.praxis.database.repositories.EventRepository;
import mops.hhu.de.rheinjug1.praxis.database.repositories.SignatureRepository;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ChartServiceTest {

  private final List<Event> sampleData = new LinkedList<>();
  private MeetupService meetupServiceMock;
  private ChartService chartService;
  private EventRepository eventRepository;
  private SignatureRepository signatureRepository;

  @BeforeEach
  void init() {
    final String time = "12:30 - 12.03.2020";
    sampleData.add(new Event("", 1, "", "", time, "", "", MeetupType.RHEINJUG));
    sampleData.add(new Event("", 2, "", "", time, "", "", MeetupType.RHEINJUG));
    sampleData.add(new Event("", 3, "", "", time, "", "", MeetupType.ENTWICKELBAR));
    this.meetupServiceMock = mock(MeetupService.class);
    this.eventRepository = spy(EventRepository.class);
    this.signatureRepository = mock(SignatureRepository.class);
    this.chartService =
        new ChartService(meetupServiceMock, eventRepository, signatureRepository, new FormatService());
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
  void testCountingReceiptsByMeetupType() {
    final Iterable<SignatureRecord> sampleReceipts =
        Arrays.asList(
            new SignatureRecord("", 1L),
            new SignatureRecord("", 2L),
            new SignatureRecord("", 2L),
            new SignatureRecord("", 3L));
    doReturn(Optional.of(sampleData.get(0))).when(eventRepository).findById(1L);
    doReturn(Optional.of(sampleData.get(1))).when(eventRepository).findById(2L);
    doReturn(Optional.of(sampleData.get(2))).when(eventRepository).findById(3L);
    doReturn(sampleReceipts).when(signatureRepository).findAll();

    assertThat(chartService.getNumberOfReceiptsByMeetupType(MeetupType.RHEINJUG)).isEqualTo(3);
    assertThat(chartService.getNumberOfReceiptsByMeetupType(MeetupType.ENTWICKELBAR)).isEqualTo(1);
  }

  @Test
  void testEmptyReceiptsRepository() {
    final Iterable<SignatureRecord> sampleReceipts = new LinkedList<>();
    doReturn(Optional.of(sampleData.get(0))).when(eventRepository).findById(Mockito.anyLong());
    doReturn(sampleReceipts).when(signatureRepository).findAll();

    assertThat(chartService.getNumberOfReceiptsByMeetupType(MeetupType.RHEINJUG)).isEqualTo(0);
    assertThat(chartService.getNumberOfReceiptsByMeetupType(MeetupType.ENTWICKELBAR)).isEqualTo(0);
  }
}
