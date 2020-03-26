package mops.hhu.de.rheinjug1.praxis.domain.chart;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import mops.hhu.de.rheinjug1.praxis.adapters.time.TimeFormatServiceImpl;
import mops.hhu.de.rheinjug1.praxis.domain.TimeFormatService;

@Getter
@Setter
@Builder
@ToString
public class ChartData
    implements Comparable<
        ChartData> { // ChartData which represents some information on a specific date

  private final String date;
  private final int submissions;
  private final int accepted;
  private final int receipts;

  @Override
  public int compareTo(final ChartData other) { // needed to sort the ChartData List after date
    final TimeFormatService timeFormatService = new TimeFormatServiceImpl();
    final LocalDate thisDateTime = timeFormatService.getLocalDate(date);
    final LocalDate otherDateTime = timeFormatService.getLocalDate(other.getDate());
    return thisDateTime.compareTo(otherDateTime);
  }
}
