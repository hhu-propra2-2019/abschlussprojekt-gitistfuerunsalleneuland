package mops.hhu.de.rheinjug1.praxis.models;

import com.google.gson.Gson;
import java.util.*;
import java.util.stream.Collectors;

public class Chart { // contains the ChartData and represents them as JSON String

  private final List<ChartData> chartData = new ArrayList<>();
  private final Gson gson = new Gson();

  public Chart(final List<ChartData> chartData) {
    this.chartData.addAll(chartData);
  }

  public String getDates() {
    final List<String> dates =
        chartData.stream().map(i -> i.getDate()).collect(Collectors.toList());
    return gson.toJson(dates);
  }

  public String getParticipants() {
    final List<Integer> participants =
        chartData.stream().map(i -> i.getSubmissions()).collect(Collectors.toList());
    return gson.toJson(participants);
  }

  public int getTalksLength() {
    return chartData.size();
  }
}
