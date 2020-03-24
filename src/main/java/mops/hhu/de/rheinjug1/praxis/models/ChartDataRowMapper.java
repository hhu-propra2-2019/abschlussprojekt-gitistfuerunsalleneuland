package mops.hhu.de.rheinjug1.praxis.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import mops.hhu.de.rheinjug1.praxis.services.TimeFormatService;
import org.springframework.jdbc.core.RowMapper;

public class ChartDataRowMapper implements RowMapper<ChartData> {

  private final TimeFormatService timeFormatService = new TimeFormatService();

  @Override
  public ChartData mapRow(final ResultSet rs, final int rowNum) throws SQLException {
    return ChartData.builder()
        .datetime(timeFormatService.extractDate(rs.getString("zoned_date_time")))
        .submissions(rs.getInt("submissions"))
        .accepted(rs.getInt("accepted"))
        .receipts(rs.getInt("receipts"))
        .build();
  }
}
