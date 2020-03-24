package mops.hhu.de.rheinjug1.praxis.domain.event;

import mops.hhu.de.rheinjug1.praxis.domain.Account;
import mops.hhu.de.rheinjug1.praxis.domain.submission.eventinfo.SubmissionEventInfo;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public interface MeetupService {
    @Scheduled(cron = "0 0 8 * * ?")
    void update();

    List<SubmissionEventInfo> getAllEventsWithInfosByEmail(Account account);

    int getSubmissionCount(Event event);

    List<Event> getEventsByStatus(String status);

    List<Event> getLastXEvents(int x);

    Event getEventIfExistent(Long meetUpId) throws EventNotFoundException;
}
