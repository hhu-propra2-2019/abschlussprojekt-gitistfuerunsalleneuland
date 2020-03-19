package mops.hhu.de.rheinjug1.praxis.services;

import java.util.List;
import mops.hhu.de.rheinjug1.praxis.database.repositories.SubmissionEventInfoRepository;
import mops.hhu.de.rheinjug1.praxis.models.Account;
import mops.hhu.de.rheinjug1.praxis.models.SubmissionEventInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubmissionEventInfoService {

  private final SubmissionEventInfoRepository submissionEventInfoRepository;

  @Autowired
  public SubmissionEventInfoService(
      final SubmissionEventInfoRepository submissionEventInfoRepository) {
    this.submissionEventInfoRepository = submissionEventInfoRepository;
  }

  public List<SubmissionEventInfo> getAllSubmissionsWithInfosByUser(final Account account) {
    return submissionEventInfoRepository.getSubmissionInfoListByEmail(account.getEmail());
  }

  public List<SubmissionEventInfo> getAllEventsWithInfosByEmail(final Account account) {
    return submissionEventInfoRepository.getAllEventsWithUserInfosByEmail(account.getEmail());
  }
}
