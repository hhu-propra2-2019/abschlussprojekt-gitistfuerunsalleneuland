package mops.hhu.de.rheinjug1.praxis.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
  private static final int DEFAULT_NUMBER_DATAPOINTS = 6;

  public int getNumberOfReceiptsByMeetupType(final MeetupType meetupType) {
    return signatureRepository.countSignatureByMeetupType(meetupType.databaseRepresentation());
  }

  public Chart getXEventsChart(final Optional<String> datapoints) {
    final int limit = getNumberDatapoints(datapoints);
    final List<ChartData> chartData = chartDataRepository.getAll();
    Collections.sort(chartData);
    final int n = chartData.size();
    if (limit >= n || limit < 0) {
      return new Chart(chartData);
    }
    return new Chart(chartData.subList(n - limit, n)); // get the last "limit" dates
  }

  public int getDefaultNumberDatapoints() {
    return DEFAULT_NUMBER_DATAPOINTS;
  }

  private int getNumberDatapoints(final Optional<String> datapoints) {
    if (datapoints == null) {
      return DEFAULT_NUMBER_DATAPOINTS;
    }
    if (datapoints.isPresent()) {
      try {
        return Integer.parseInt(datapoints.get());
      } catch (NumberFormatException e) {
        return DEFAULT_NUMBER_DATAPOINTS;
      }
    }
    return DEFAULT_NUMBER_DATAPOINTS;
  }
}
