package mops.hhu.de.rheinjug1.praxis.database.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignatureRecord {

  @Id private String signature;
  private Long meetupId;
}
