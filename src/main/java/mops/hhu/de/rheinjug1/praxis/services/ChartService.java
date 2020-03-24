package mops.hhu.de.rheinjug1.praxis.services;

import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.database.repositories.ChartDataRepository;
import mops.hhu.de.rheinjug1.praxis.database.repositories.SignatureRepository;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.models.Chart;
import mops.hhu.de.rheinjug1.praxis.models.ChartData;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ChartService {

  private final SignatureRepository signatureRepository;
  private final ChartDataRepository chartDataRepository;

  public int getNumberOfReceiptsByMeetupType(final MeetupType meetupType) {
    return signatureRepository.countSignatureByMeetupType(meetupType.databaseRepresentation());
  }

  public Chart getXEventsChart(final int limit) {
    final List<ChartData> chartData = chartDataRepository.getAll();
    final int n = chartData.size();
    Collections.sort(chartData);      //Sort ChartData after date asc
    return new Chart(chartData.subList(n - limit, n));    //get the last "limit" dates
  }
}
