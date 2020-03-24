package mops.hhu.de.rheinjug1.praxis.adapters.database.submission.eventinfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import mops.hhu.de.rheinjug1.praxis.domain.submission.eventinfo.SubmissionEventInfo;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import org.springframework.jdbc.core.RowMapper;

public class SubmissionEventInfoRowMapper implements RowMapper<SubmissionEventInfo> {

  @Override
  public SubmissionEventInfo mapRow(final ResultSet rs, final int rowNum) throws SQLException {
    return SubmissionEventInfo.builder()
        .id(rs.getLong("id"))
        .meetupId(rs.getLong("meetup_id"))
        .eventTitle(rs.getString("meetup_title"))
        .minIoLink(rs.getString("min_io_link"))
        .eventLink(rs.getString("link"))
        .eventDateTime(rs.getString("zoned_date_time"))
        .meetupType(MeetupType.valueOf(rs.getString("meetup_type")))
        .accepted(rs.getBoolean("accepted"))
        .name(rs.getString("name"))
        .build();
  }
}
