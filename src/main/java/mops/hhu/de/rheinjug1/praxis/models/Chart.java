package mops.hhu.de.rheinjug1.praxis.models;

import com.google.gson.Gson;
import java.util.*;

public class Chart {

  private final List<String> dates = new ArrayList<>();
  private final List<Integer> participants = new ArrayList<>();
  private final Gson gson = new Gson();

  public Chart(final List<String> dates, final List<Integer> participants) {
    this.dates.addAll(dates);
    this.participants.addAll(participants);
  }

  public String getDates() {
    return gson.toJson(this.dates);
  }

  public String getParticipants() {
    return gson.toJson(this.participants);
  }

  public int getTalksLength() {
    return dates.size();
  }
}
