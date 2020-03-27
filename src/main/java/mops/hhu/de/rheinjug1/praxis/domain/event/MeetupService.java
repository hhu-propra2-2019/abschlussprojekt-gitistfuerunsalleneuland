package mops.hhu.de.rheinjug1.praxis.domain.event;

import java.util.List;
import mops.hhu.de.rheinjug1.praxis.domain.Account;
import mops.hhu.de.rheinjug1.praxis.domain.submission.eventinfo.SubmissionEventInfo;
import org.springframework.scheduling.annotation.Scheduled;

public interface MeetupService {
  @Scheduled(cron = "0 0 8 * * ?")
  void update();

  List<SubmissionEventInfo> getAllEventsWithInfosByUser(Account account);

  int getSubmissionCount(Event event);

  List<Event> getEventsByStatus(String status);

  Event getEventIfExistent(Long meetUpId) throws EventNotFoundException;
}
