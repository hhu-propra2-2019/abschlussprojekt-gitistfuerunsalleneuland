package mops.hhu.de.rheinjug1.praxis.domain;

import lombok.Data;
import mops.hhu.de.rheinjug1.praxis.enums.Sex;

import java.util.ArrayList;
import java.util.List;

@Data
public class CertificationData {

  private String firstname;
  private String lastname;
  private String studentNumber;
  private Sex sex;
  private String date;
  private String type;
  private List<String> eventTitles = new ArrayList<>(3);

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
