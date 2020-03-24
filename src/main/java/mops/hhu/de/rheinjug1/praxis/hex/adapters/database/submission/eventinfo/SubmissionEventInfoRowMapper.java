package mops.hhu.de.rheinjug1.praxis.hex.adapters.database.submission.eventinfo;

import mops.hhu.de.rheinjug1.praxis.hex.domain.submission.eventinfo.SubmissionEventInfo;
import mops.hhu.de.rheinjug1.praxis.hex.enums.MeetupType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubmissionEventInfoRowMapper implements RowMapper<SubmissionEventInfo> {

  @Override
  public SubmissionEventInfo mapRow(final ResultSet rs, final int rowNum) throws SQLException {
    return SubmissionEventInfo.builder()
        .id(rs.getLong("id"))
        .meetupId(rs.getLong("meetup_id"))
        .eventName(rs.getString("name"))
        .minIoLink(rs.getString("min_io_link"))
        .eventLink(rs.getString("link"))
        .eventDateTime(rs.getString("zoned_date_time"))
        .meetupType(MeetupType.valueOf(rs.getString("meetup_type")))
        .accepted(rs.getBoolean("accepted"))
        .build();
  }
}
