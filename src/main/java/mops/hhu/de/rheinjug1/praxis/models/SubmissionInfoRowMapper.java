package mops.hhu.de.rheinjug1.praxis.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import org.springframework.jdbc.core.RowMapper;

public class SubmissionInfoRowMapper implements RowMapper<SubmissionInfo> {

  @Override
  public SubmissionInfo mapRow(final ResultSet rs, final int rowNum) throws SQLException {
    return SubmissionInfo.builder()
        .id(rs.getLong("id"))
        .meetupId(rs.getLong("meetup_id"))
        .eventName(rs.getString("name"))
        .minIoLink(rs.getString("min_io_link"))
        .eventLink(rs.getString("link"))
        .meetupType(MeetupType.valueOf(rs.getString("meetup_type")))
        .accepted(rs.getBoolean("accepted"))
        .build();
  }
}
