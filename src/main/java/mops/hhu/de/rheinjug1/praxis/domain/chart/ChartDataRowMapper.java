package mops.hhu.de.rheinjug1.praxis.domain.chart;

import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.RequiredArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.domain.TimeFormatService;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChartDataRowMapper implements RowMapper<ChartData> {

  private final TimeFormatService timeFormatService;

  @Override
  public ChartData mapRow(final ResultSet rs, final int rowNum) throws SQLException {
    return ChartData.builder()
        .date(timeFormatService.extractDate(rs.getString("zoned_date_time")))
        .submissions(rs.getInt("submissions"))
        .accepted(rs.getInt("accepted"))
        .receipts(rs.getInt("receipts"))
        .build();
  }
}
