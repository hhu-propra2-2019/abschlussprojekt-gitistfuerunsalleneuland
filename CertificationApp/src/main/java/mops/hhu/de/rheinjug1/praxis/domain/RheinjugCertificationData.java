package mops.hhu.de.rheinjug1.praxis.domain;

import java.util.List;
import lombok.Data;
import mops.hhu.de.rheinjug1.praxis.enums.Sex;

@Data
public class RheinjugCertificationData {

  private String firstname;
  private String lastname;
  private String studentNumber;
  private Sex sex;
  private String type;
  private List<String> eventTitles;

  public RheinjugCertificationData(final FormUserData formUserData) {
    this.firstname = formUserData.getFirstname();
    this.lastname = formUserData.getLastname();
    this.sex = "Herr".equals(formUserData.getSalutation()) ? Sex.MALE : Sex.FEMALE;
    this.studentNumber = formUserData.getStudentNumber();
  }

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
