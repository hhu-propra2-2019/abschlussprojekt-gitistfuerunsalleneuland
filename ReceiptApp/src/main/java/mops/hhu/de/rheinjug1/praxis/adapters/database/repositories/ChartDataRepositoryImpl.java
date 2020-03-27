package mops.hhu.de.rheinjug1.praxis.adapters.database.repositories;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.domain.chart.ChartData;
import mops.hhu.de.rheinjug1.praxis.domain.chart.ChartDataRepository;
import mops.hhu.de.rheinjug1.praxis.domain.chart.ChartDataRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChartDataRepositoryImpl
    implements ChartDataRepository { // interface to the view rheinjug1.data_chart needed for
  // statisticts
  // view

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final ChartDataRowMapper chartDataRowMapper;

  @Override
  public List<ChartData> getAll() {
    final MapSqlParameterSource paramSource = new MapSqlParameterSource();
    final String query = "SELECT * FROM rheinjug1.chart_data";
    return jdbcTemplate.query(query, paramSource, chartDataRowMapper);
  }
}
