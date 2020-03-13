package mops.hhu.de.rheinjug1.praxis.enums;

import lombok.Getter;

@Getter
public enum MeetupType {
  RHEINJUG("Rheinjug"),
  ENTWICKELBAR("Entwickelbar");

  private final String label;

  MeetupType(final String label) {
    this.label = label;
  };
}
