package mops.hhu.de.rheinjug1.praxis.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import mops.hhu.de.rheinjug1.praxis.database.repositories.ChartDataRepository;
import mops.hhu.de.rheinjug1.praxis.database.repositories.SignatureRepository;
import mops.hhu.de.rheinjug1.praxis.models.Chart;
import mops.hhu.de.rheinjug1.praxis.models.ChartData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChartServiceTest {

  private final List<ChartData> sampleData = new LinkedList<>();
  private ChartDataRepository chartDataRepositorymock;
  private ChartService chartService;
  private int defaultNumberDatapoints;
  static final String TIME = "12.03.2020";

  @BeforeEach
  void init() {
    sampleData.clear();
    this.chartDataRepositorymock = mock(ChartDataRepository.class);
    final SignatureRepository signatureRepositorymock = mock(SignatureRepository.class);

    this.chartService = new ChartService(signatureRepositorymock, chartDataRepositorymock);
    this.defaultNumberDatapoints = chartService.getDefaultNumberDatapoints();
  }

  private List<ChartData> createSampleData(final int n) {
    for (int i = 0; i < n; i++) {
      sampleData.add(ChartData.builder().date(TIME).submissions(1).accepted(1).receipts(1).build());
    }
    return sampleData;
  }

  @Test
  void testGetXEventsChartOptional() {
    when(chartDataRepositorymock.getAll()).thenReturn(createSampleData(3));

    final int numberOfTalks =
        chartService.getXEventsChart(Optional.of(String.valueOf(2))).getTalksLength();

    verify(chartDataRepositorymock).getAll();
    assertThat(numberOfTalks).isEqualTo(2);
  }

  @Test
  void testGetXEventsChartBigInt() {
    when(chartDataRepositorymock.getAll()).thenReturn(createSampleData(3));

    final int numberOfTalks =
        chartService.getXEventsChart(Optional.of(String.valueOf(5))).getTalksLength();

    assertThat(numberOfTalks).isEqualTo(sampleData.size());
  }

  @Test
  void testGetXEventsChartNullOptional() {
    when(chartDataRepositorymock.getAll()).thenReturn(createSampleData(3));

    final Chart data = chartService.getXEventsChart(null);

    assertThat(data.getTalksLength()).isEqualTo(sampleData.size());
  }

  @Test
  void testGetXEventsChartNullBigSample() {
    when(chartDataRepositorymock.getAll()).thenReturn(createSampleData(10));

    final Chart data = chartService.getXEventsChart(null);

    assertThat(data.getTalksLength()).isEqualTo(defaultNumberDatapoints);
  }

  @Test
  void testGetXEventsChartEmptyOptionl() {
    when(chartDataRepositorymock.getAll()).thenReturn(createSampleData(10));

    final Chart data = chartService.getXEventsChart(Optional.empty());

    assertThat(data.getTalksLength()).isEqualTo(defaultNumberDatapoints);
  }

  @Test
  void testGetXEventsChartNegativOptional() {
    when(chartDataRepositorymock.getAll()).thenReturn(createSampleData(5));

    final Chart data = chartService.getXEventsChart(Optional.of(String.valueOf(-1)));

    assertThat(data.getTalksLength()).isEqualTo(5);
  }
}
