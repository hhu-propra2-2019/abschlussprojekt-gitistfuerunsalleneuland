package mops.hhu.de.rheinjug1.praxis.models;

import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.IntStream;

public class Chart {

  private List<String> dates = new ArrayList<>();
  private List<Integer> participants = new ArrayList<>();
  private final Gson gson = new Gson();

  public Chart(final List<String> dates, final List<Integer> participants) {
    this.dates.addAll(dates);
    this.participants.addAll(participants);
  }

  public String getDates() {
    return gson.toJson(this.dates);
  }

  public String getData() {
    return gson.toJson(this.participants);
  }

  public int getTalksLength() {
    return dates.size();
  }
}
