package mops.hhu.de.rheinjug1.praxis.database.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SignatureRecord {
  @Id private String signature;
  private Long meetupId;

  public SignatureRecord(final String signature, final Long meetupId) {
    this.signature = signature;
    this.meetupId = meetupId;
  }
}
