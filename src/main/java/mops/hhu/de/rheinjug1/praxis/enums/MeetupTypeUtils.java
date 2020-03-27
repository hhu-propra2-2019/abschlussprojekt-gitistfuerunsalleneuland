package mops.hhu.de.rheinjug1.praxis.enums;

import static mops.hhu.de.rheinjug1.praxis.enums.MeetupType.ENTWICKELBAR;
import static mops.hhu.de.rheinjug1.praxis.enums.MeetupType.RHEINJUG;

import java.util.Locale;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MeetupTypeUtils {
  public static MeetupType extractMeetupTypeFromString(final String s) {
    return s.toLowerCase(Locale.ROOT).contains("entwickelbar") ? ENTWICKELBAR : RHEINJUG;
  }
}
