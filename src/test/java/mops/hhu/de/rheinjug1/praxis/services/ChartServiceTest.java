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

  @BeforeEach
  void init() {
    final String time = "12:30 - 12.03.2020";
    sampleData.add(new Event("", 0, "", "", time, "", "", null));
    sampleData.add(new Event("", 0, "", "", time, "", "", null));
  }

  @Test
  void testNumberOfDataPoints() {
    final MeetupService meetupServiceMock = mock(MeetupService.class);
    final ChartService chartService = new ChartService(meetupServiceMock);
    when(meetupServiceMock.getLastXEvents(Mockito.anyInt())).thenReturn(sampleData);
    final int numberOfTalks = chartService.getXEventsChart(2).getTalksLength();

    assertThat(numberOfTalks).isEqualTo(sampleData.size());
  }
}
