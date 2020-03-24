package mops.hhu.de.rheinjug1.praxis.database.repositories;

import java.util.List;
import mops.hhu.de.rheinjug1.praxis.models.ChartData;
import mops.hhu.de.rheinjug1.praxis.models.ChartDataRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ChartDataRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  @Autowired
  public ChartDataRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<ChartData> getAllLimited() {
    final MapSqlParameterSource paramSource = new MapSqlParameterSource();
    final String query = "SELECT * FROM rheinjug1.chart_data";
    final ChartDataRowMapper chartDataRowMapper = new ChartDataRowMapper();
    return jdbcTemplate.query(query, paramSource, chartDataRowMapper);
  }
}
