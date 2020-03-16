package mops.hhu.de.rheinjug1.praxis.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Chart {

  private final List<Talk> talks = new LinkedList<>();

  public Chart(final List<Talk> toBeAddedTalks) {
    talks.addAll(toBeAddedTalks);
  }

  public String getDates() {
    final List<String> dates =
        talks.stream()
            .map(x -> toLocalDateString(x.getDate()))
            .map(x -> encaseDate(x))
            .collect(Collectors.toList());
    return encase(String.join(",", dates));
  }

  public String getData() {
    final List<String> dates =
        talks.stream().map(x -> String.valueOf(x.getParticipant())).collect(Collectors.toList());
    return encase(String.join(",", dates));
  }

  public void addTalks(final List<Talk> toBeAddedTalks) {
    talks.addAll(toBeAddedTalks);
  }

  public void addTalk(final Talk toBeAddedTalk) {
    talks.add(toBeAddedTalk);
  }

  public int getTalksLength() {
    return talks.size();
  }

  private String encase(final String content) {
    return String.format("[%s]", content);
  }

  private String encaseDate(final String date) {
    return String.format("'%s'", date);
  }

  private String toLocalDateString(final LocalDateTime date) {
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    return date.format(formatter);
  }
}
