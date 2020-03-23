package mops.hhu.de.rheinjug1.praxis.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {

	private String email;
  private String name;
  private long keycloakId;
  private long meetupId;
  private String meetupTitle;
  private String meetupType;
  private String signature;

  public Receipt cloneThisReceipt() {
    return new Receipt(email, name, keycloakId, meetupId, meetupType, meetupTitle, signature);
  }
}
