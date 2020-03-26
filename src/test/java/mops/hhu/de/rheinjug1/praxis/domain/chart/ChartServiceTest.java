package mops.hhu.de.rheinjug1.praxis.domain.chart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import mops.hhu.de.rheinjug1.praxis.domain.receipt.SignatureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChartServiceTest {

  private final List<ChartData> sampleData = new LinkedList<>();
  private ChartDataRepository chartDataRepositorymock;
  private ChartService chartService;

  @BeforeEach
  void init() {
    final String time = "12.03.2020";
    sampleData.add(ChartData.builder().date(time).submissions(1).accepted(1).receipts(1).build());
    sampleData.add(ChartData.builder().date(time).submissions(1).accepted(1).receipts(1).build());
    sampleData.add(ChartData.builder().date(time).submissions(1).accepted(1).receipts(1).build());
    this.chartDataRepositorymock = mock(ChartDataRepository.class);
    final SignatureRepository signatureRepository = mock(SignatureRepository.class);
    this.chartService = new ChartService(signatureRepository, chartDataRepositorymock);
  }

  @Test
  void testNumberOfDataPoints() { // Arrange
    when(chartDataRepositorymock.getAll()).thenReturn(sampleData);
    // Act
    final int numberOfTalks = chartService.getXEventsChart(2).getTalksLength();
    // Assert
    assertThat(numberOfTalks).isEqualTo(2);
  }
}
