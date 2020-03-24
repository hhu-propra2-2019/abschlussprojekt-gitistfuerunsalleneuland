package mops.hhu.de.rheinjug1.praxis.hex.domain.receipt;

import lombok.*;
import mops.hhu.de.rheinjug1.praxis.hex.annotations.DB;
import org.springframework.data.annotation.Id;

@DB
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignatureRecord {

  @Id private String signature;
  private Long meetupId;
}
