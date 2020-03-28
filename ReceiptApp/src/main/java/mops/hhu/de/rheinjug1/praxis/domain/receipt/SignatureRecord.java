package mops.hhu.de.rheinjug1.praxis.domain.receipt;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignatureRecord {

  @Id private byte[] signature;
  private Long meetupId;
}
