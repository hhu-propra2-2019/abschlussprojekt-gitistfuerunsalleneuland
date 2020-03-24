package mops.hhu.de.rheinjug1.praxis.domain.receipt;

import lombok.*;
import mops.hhu.de.rheinjug1.praxis.annotations.DB;
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
