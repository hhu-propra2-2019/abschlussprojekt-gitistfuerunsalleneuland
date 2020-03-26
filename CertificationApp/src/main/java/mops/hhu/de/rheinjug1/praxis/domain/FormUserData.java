package mops.hhu.de.rheinjug1.praxis.domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FormUserData {

  private String firstname;
  private String lastname;
  private String studentNumber;
  private String salutation;
  private MultipartFile firstRheinjugReceipt;
  private MultipartFile secondRheinjugReceipt;
  private MultipartFile thirdRheinjugReceipt;
  private MultipartFile entwickelbarReceipt;
}
