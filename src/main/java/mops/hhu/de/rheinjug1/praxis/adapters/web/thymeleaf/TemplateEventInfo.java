package mops.hhu.de.rheinjug1.praxis.adapters.web.thymeleaf;

import static org.springframework.beans.BeanUtils.copyProperties;

import lombok.Data;
import mops.hhu.de.rheinjug1.praxis.domain.TimeFormatService;
import mops.hhu.de.rheinjug1.praxis.domain.submission.eventinfo.SubmissionEventInfo;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;

@Data
public class TemplateEventInfo {
  private Long id;
  private Long meetupId;
  private String minIoLink;
  private boolean accepted;
  private String eventTitle;
  private String eventLink;
  private String eventDateTime;
  private MeetupType meetupType;
  private String name;
  private String germanDateString;
  private boolean uploadPeriodExpired;
  private boolean inUploadPeriod;
  private boolean inTheFuture;

  public TemplateEventInfo(
      final SubmissionEventInfo eventInfo, final TimeFormatService timeFormatService) {
    copyProperties(eventInfo, this);
    this.germanDateString = timeFormatService.getGermanDateString(eventInfo);
    this.uploadPeriodExpired = timeFormatService.isUploadPeriodExpired(eventInfo);
    this.inUploadPeriod = timeFormatService.isInUploadPeriod(eventInfo);
    this.inTheFuture = timeFormatService.isInTheFuture(eventInfo);
  }
}
