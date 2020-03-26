package mops.hhu.de.rheinjug1.praxis.domain.receipt;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receipt implements Serializable, Cloneable {

  private String name;
  private String email;
  private Long meetupId;
  private String meetupTitle;
  private MeetupType meetupType;
  private String signature;

}
