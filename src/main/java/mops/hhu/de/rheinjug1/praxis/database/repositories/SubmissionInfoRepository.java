package mops.hhu.de.rheinjug1.praxis.database.repositories;

import java.util.List;
import mops.hhu.de.rheinjug1.praxis.models.SubmissionInfo;
import mops.hhu.de.rheinjug1.praxis.models.SubmissionInfoRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SubmissionInfoRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  @Autowired
  public SubmissionInfoRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<SubmissionInfo> getSubmissionInfoListByEmail(final String email) {
    final MapSqlParameterSource paramSource = new MapSqlParameterSource();
    paramSource.addValue("email", email);

    final String query =
        "SELECT e.name, e.link, e.meetup_type, s.id, s.meetup_id, s.min_io_link, s.accepted FROM rheinjug1.submission s INNER JOIN rheinjug1.event e ON s.meetup_id = e.id WHERE s.email = :email";

    final SubmissionInfoRowMapper rowMapper = new SubmissionInfoRowMapper();
    return jdbcTemplate.query(query, paramSource, rowMapper);
  }
}
