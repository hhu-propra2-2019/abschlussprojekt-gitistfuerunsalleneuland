package mops.hhu.de.rheinjug1.praxis.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {

  private String name;
  private long keycloakId;
  private long meetupId;
  private String type; // sollte ein enum sein
  private String signature;
  private String error;

  public Receipt cloneThisReceipt() {
    return new Receipt(name, keycloakId, meetupId, type, signature, error);
  }
}
