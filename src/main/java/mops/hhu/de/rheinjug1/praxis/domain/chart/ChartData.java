package mops.hhu.de.rheinjug1.praxis.domain.chart;

import java.time.LocalDate;
import lombok.*;
import mops.hhu.de.rheinjug1.praxis.domain.TimeFormatService;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ChartData
    implements Comparable<
        ChartData> { // ChartData which represents some information on a specific date

  private final String date;
  private final int submissions;
  private final int accepted;
  private final int receipts;
  private final TimeFormatService timeFormatService;

  @Override
  public int compareTo(final ChartData other) { // needed to sort the ChartData List after date
    final LocalDate thisDateTime = timeFormatService.getLocalDate(date);
    final LocalDate otherDateTime = timeFormatService.getLocalDate(other.getDate());
    return thisDateTime.compareTo(otherDateTime);
  }

  // Standard Lombok builder, with TimeFormatService injected
  @RequiredArgsConstructor
  @Component
  @SuppressWarnings({"PMD.AvoidFieldNameMatchingMethodName", "PMD.MethodArgumentCouldBeFinal"})
  public static class ChartDataBuilder {
    private String date;
    private int submissions;
    private int accepted;
    private int receipts;
    private final TimeFormatService timeFormatService;

    public ChartDataBuilder date(String date) {
      this.date = date;
      return this;
    }

    public ChartDataBuilder submissions(int submissions) {
      this.submissions = submissions;
      return this;
    }

    public ChartDataBuilder accepted(int accepted) {
      this.accepted = accepted;
      return this;
    }

    public ChartDataBuilder receipts(int receipts) {
      this.receipts = receipts;
      return this;
    }

    public ChartData build() {
      return new ChartData(date, submissions, accepted, receipts, timeFormatService);
    }

    @Override
    public String toString() {
      return "ChartData.ChartDataBuilder(date="
          + this.date
          + ", submissions="
          + this.submissions
          + ", accepted="
          + this.accepted
          + ", receipts="
          + this.receipts
          + ")";
    }
  }
}
