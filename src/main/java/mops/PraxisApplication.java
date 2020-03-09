package mops;

import java.util.List;
import mops.hhu.de.rheinjug1.praxis.clients.MeetupClient;
import mops.hhu.de.rheinjug1.praxis.database.entities.Event;
import mops.hhu.de.rheinjug1.praxis.database.repositories.ReceiptRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SuppressWarnings("PMD")
@SpringBootApplication
public class PraxisApplication {

  public static void main(String[] args) {
    SpringApplication.run(PraxisApplication.class, args);
  }

  @Bean
  ApplicationRunner init(final MeetupClient meetupClient, ReceiptRepository r) {
    return args -> {
      List<Event> all = meetupClient.getAllEvents();
      all.stream().filter(i->i.getId()==269005066).map(i->i.getDuration().toMinutes()).forEach(i-> System.out.println(i));
    };
  };
}
