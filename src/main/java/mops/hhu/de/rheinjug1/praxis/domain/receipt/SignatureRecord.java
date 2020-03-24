package mops.hhu.de.rheinjug1.praxis.domain.receipt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignatureRecord {

  private String signature;
  private Long meetupId;
}
