package mops.hhu.de.rheinjug1.praxis.services;

import java.util.List;
import javax.sql.DataSource;
import mops.hhu.de.rheinjug1.praxis.clients.MeetupClient;
import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import mops.hhu.de.rheinjug1.praxis.database.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@SuppressWarnings({"PMD.UnusedPrivateField", "PMD.UnusedPrivateMethod"})
@EnableScheduling
@Component
public class MeetupService {

  @Autowired private MeetupClient meetupClient;
  @Autowired private EventRepository repo;
  @Autowired DataSource dataSource;
  @Autowired JdbcAggregateTemplate template;

  @Scheduled(fixedRate = 3000) // Todo:Zeitintervall?
  private void update() {
    final List<Event> all = meetupClient.getAllEvents();
    all.stream().forEach(i -> save(i));
  }

  private void save(final Event e) {
    try {
      template.insert(e);
    } catch (DbActionExecutionException ex) {
      repo.save(e);
    }
  }
}
