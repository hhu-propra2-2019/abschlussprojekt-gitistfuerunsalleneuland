package mops.hhu.de.rheinjug1.praxis.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import mops.hhu.de.rheinjug1.praxis.database.repositories.ChartDataRepository;
import mops.hhu.de.rheinjug1.praxis.database.repositories.SignatureRepository;
import mops.hhu.de.rheinjug1.praxis.models.ChartData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChartServiceTest {

  private final List<ChartData> sampleData = new LinkedList<>();
  private ChartDataRepository chartDataRepositorymock;
  private ChartService chartService;
  // private static int defaultNumberDatapoints;

  @BeforeEach
  void init() {
    final String time = "12.03.2020";
    sampleData.add(ChartData.builder().date(time).submissions(1).accepted(1).receipts(1).build());
    sampleData.add(ChartData.builder().date(time).submissions(1).accepted(1).receipts(1).build());
    sampleData.add(ChartData.builder().date(time).submissions(1).accepted(1).receipts(1).build());
    this.chartDataRepositorymock = mock(ChartDataRepository.class);
    final SignatureRepository signatureRepository = mock(SignatureRepository.class);

    this.chartService = new ChartService(signatureRepository, chartDataRepositorymock);
    // this.defaultNumberDatapoints = chartService.getDefaultNumberDatapoints();
  }

  @Test
  void testNumberOfDataPoints() {
    // Arrange
    when(chartDataRepositorymock.getAll()).thenReturn(sampleData);
    // Act
    final int numberOfTalks =
        chartService.getXEventsChart(Optional.of(String.valueOf(2))).getTalksLength();
    // Assert
    assertThat(numberOfTalks).isEqualTo(2);
  }

  @Test
  void testGetXEventsChartOptional() {
    when(chartDataRepositorymock.getAll()).thenReturn(sampleData);

    final int numberOfTalks =
        chartService.getXEventsChart(Optional.of(String.valueOf(2))).getTalksLength();

    verify(chartDataRepositorymock).getAll();
    assertThat(numberOfTalks).isEqualTo(2);
  }

  @Test
  void testGetXEventsChartBigInt() {
    when(chartDataRepositorymock.getAll()).thenReturn(sampleData);

    final int numberOfTalks =
        chartService.getXEventsChart(Optional.of(String.valueOf(5))).getTalksLength();

    assertThat(numberOfTalks).isEqualTo(3);
  }

  @Test
  void testGetXEventsChartNullOptional() {
    when(chartDataRepositorymock.getAll()).thenReturn(sampleData);

    chartService.getXEventsChart(null);

    verify(chartDataRepositorymock).getAll();
  }

  @Test
  void testGetXEventsChartEmptyOptionl() {
    when(chartDataRepositorymock.getAll()).thenReturn(sampleData);

    chartService.getXEventsChart(Optional.empty());

    verify(chartDataRepositorymock).getAll();
  }

  @Test
  void testGetXEventsChartNegativOptional() {
    when(chartDataRepositorymock.getAll()).thenReturn(sampleData);

    chartService.getXEventsChart(Optional.of(String.valueOf(-1)));

    verify(chartDataRepositorymock, times(1)).getAll();
    verify(chartDataRepositorymock).getAll();
  }
}
