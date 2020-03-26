package mops.hhu.de.rheinjug1.praxis.database.repositories;

import java.util.List;
import mops.hhu.de.rheinjug1.praxis.domain.chart.ChartData;
import mops.hhu.de.rheinjug1.praxis.domain.chart.ChartDataRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public
class ChartDataRepository { // interface to the view rheinjug1.data_chart needed for statisticts
  // view

  private final NamedParameterJdbcTemplate jdbcTemplate;

  @Autowired
  public ChartDataRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<ChartData> getAll() {
    final MapSqlParameterSource paramSource = new MapSqlParameterSource();
    final String query = "SELECT * FROM rheinjug1.chart_data";
    final ChartDataRowMapper chartDataRowMapper = new ChartDataRowMapper();
    return jdbcTemplate.query(query, paramSource, chartDataRowMapper);
  }
}
