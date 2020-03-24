package mops.hhu.de.rheinjug1.praxis.models;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import mops.hhu.de.rheinjug1.praxis.services.TimeFormatService;

@Getter
@Setter
@Builder
@ToString
public class ChartData implements Comparable<ChartData> {

  private final String datetime;
  private final int submissions;
  private final int accepted;
  private final int receipts;

  @Override
  public int compareTo(final ChartData other) {
    final TimeFormatService timeFormatService = new TimeFormatService();
    final LocalDate thisDateTime = timeFormatService.getLocalDate(datetime);
    final LocalDate otherDateTime = timeFormatService.getLocalDate(other.getDatetime());
    return thisDateTime.compareTo(otherDateTime);
  }
}
