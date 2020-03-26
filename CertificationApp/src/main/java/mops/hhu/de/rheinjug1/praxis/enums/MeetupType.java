package mops.hhu.de.rheinjug1.praxis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum MeetupType {
  ENTWICKELBAR("Entwickelbar"),
  RHEINJUG("Rheinjug");

  @Getter private final String label;
}
