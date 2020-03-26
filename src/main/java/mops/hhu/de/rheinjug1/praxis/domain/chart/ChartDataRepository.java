package mops.hhu.de.rheinjug1.praxis.domain.chart;

import mops.hhu.de.rheinjug1.praxis.domain.chart.ChartData;

import java.util.List;

public interface ChartDataRepository {
    List<ChartData> getAll();
}
