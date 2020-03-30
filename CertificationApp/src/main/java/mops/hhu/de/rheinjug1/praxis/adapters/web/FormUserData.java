package mops.hhu.de.rheinjug1.praxis.adapters.web;

import lombok.Data;
import mops.hhu.de.rheinjug1.praxis.domain.certification.EntwickelbarCertificationData;
import mops.hhu.de.rheinjug1.praxis.domain.certification.RheinjugCertificationData;
import mops.hhu.de.rheinjug1.praxis.enums.Sex;

@Data
public class FormUserData {

  private String firstname;
  private String lastname;
  private String studentNumber;
  private String salutation;

  RheinjugCertificationData toRheinjugCertificationData() {
    return RheinjugCertificationData.builder()
        .firstname(firstname)
        .lastname(lastname)
        .studentNumber(studentNumber)
        .sex("Herr".equals(salutation) ? Sex.MALE : Sex.FEMALE)
        .build();
  }
  
  EntwickelbarCertificationData toEntwickelbarCertificationData() {
	    return EntwickelbarCertificationData.builder()
	        .firstname(firstname)
	        .lastname(lastname)
	        .studentNumber(studentNumber)
	        .sex("Herr".equals(salutation) ? Sex.MALE : Sex.FEMALE)
	        .build();
	  }
}
