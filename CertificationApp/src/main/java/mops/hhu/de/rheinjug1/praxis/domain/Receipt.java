package mops.hhu.de.rheinjug1.praxis.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Receipt {

  private String name;
  private long keycloakId;
  private long meetupId;
  private String type;
  private String signature;
}
