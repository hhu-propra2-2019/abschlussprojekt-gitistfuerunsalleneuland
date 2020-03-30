package mops.hhu.de.rheinjug1.praxis.domain.certification;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.enums.Sex;

@Data
@Builder
public class RheinjugCertificationData implements CertificationData {

  private String firstname;
  private String lastname;
  private String studentNumber;
  private Sex sex;
  private MeetupType type;
  private List<String> eventTitles;

  public String getSalutation() {

    if (sex == Sex.MALE) {
      return "Herr";
    }
    return "Frau";
  }

  public String getPronoun() {

    if (sex == Sex.MALE) {
      return "er";
    }
    return "sie";
  }
}
