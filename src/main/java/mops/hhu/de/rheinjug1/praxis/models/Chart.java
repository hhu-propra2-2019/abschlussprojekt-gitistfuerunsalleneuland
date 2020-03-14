package mops.hhu.de.rheinjug1.praxis.models;

import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;

@SuppressWarnings({"PMD.ConstructorCallsOverridableMethod", "PMD.LooseCoupling"})
@Getter
public class Chart {

  private String dates = "[";
  private String data = "[";
  private LinkedList<Talk> talks = new LinkedList<>();

  public Chart(final List<Talk> toBeAddedTalks) {
    addTalks(toBeAddedTalks);
    render();
  }

  public void addTalks(final List<Talk> toBeAddedTalks) {
    talks.addAll(toBeAddedTalks);
    resetStrings();
  }

  public void addTalk(final Talk toBeAddedTalk) {
    talks.add(toBeAddedTalk);
    resetStrings();
  }

  public void render() {
    final Talk last = talks.removeLast();
    for (final Talk current : talks) {
      addDataPoint(current);
      addComma();
    }
    addDataPoint(last);
    finishString();
  }

  private void addDataPoint(final Talk current) {
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    dates = dates.concat("'" + current.getDate().format(formatter) + "'");
    data = data.concat(String.valueOf(current.getParticipant()));
  }

  private void addComma() {
    dates = dates.concat(",");
    data = data.concat(",");
  }

  private void resetStrings() {
    dates = "[";
    data = "[";
  }

  private void finishString() {
    dates = dates.concat("]");
    data = data.concat("]");
  }
}
