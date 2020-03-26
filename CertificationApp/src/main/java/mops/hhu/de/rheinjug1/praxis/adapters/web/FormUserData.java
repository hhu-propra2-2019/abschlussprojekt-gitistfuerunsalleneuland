package mops.hhu.de.rheinjug1.praxis.adapters.web;

import lombok.Data;
import mops.hhu.de.rheinjug1.praxis.domain.certification.CertificationData;
import mops.hhu.de.rheinjug1.praxis.enums.Sex;

@Data
public class FormUserData {

  private String firstname;
  private String lastname;
  private String studentNumber;
  private String salutation;

  CertificationData toRheinjugCertificationData() {
    return CertificationData.builder()
        .firstname(firstname)
        .lastname(lastname)
        .studentNumber(studentNumber)
        .sex("Herr".equals(salutation) ? Sex.MALE : Sex.FEMALE)
        .build();
  }
}
