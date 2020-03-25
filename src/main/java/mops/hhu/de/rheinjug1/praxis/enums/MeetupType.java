package mops.hhu.de.rheinjug1.praxis.enums;

import java.util.Locale;
import lombok.Getter;

@Getter
public enum MeetupType {
  RHEINJUG("Rheinjug"),
  ENTWICKELBAR("Entwickelbar");

  private final String label;

  MeetupType(final String label) {
    this.label = label;
  };

  public String databaseRepresentation() {
    return label.toUpperCase(Locale.GERMANY);
  }
}
