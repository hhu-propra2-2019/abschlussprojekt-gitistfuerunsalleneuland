package mops.hhu.de.rheinjug1.praxis.enums;

import lombok.Getter;

@Getter
@SuppressWarnings("PMD.UseLocaleWithCaseConversions") // Violation in conflict with
// PMD.UnnecessaryLocalBeforeReturn
public enum MeetupType {
  RHEINJUG("Rheinjug"),
  ENTWICKELBAR("Entwickelbar");

  private final String label;

  MeetupType(final String label) {
    this.label = label;
  };

  public String databaseRepresentation() { // In database meetup_type is saved in Uppercase
    return label.toUpperCase();
  }
}
