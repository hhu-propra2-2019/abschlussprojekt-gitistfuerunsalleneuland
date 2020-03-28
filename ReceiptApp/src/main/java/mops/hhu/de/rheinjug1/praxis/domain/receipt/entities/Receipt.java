package mops.hhu.de.rheinjug1.praxis.domain.receipt.entities;

import static org.springframework.beans.BeanUtils.copyProperties;

import lombok.*;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Receipt implements Cloneable {

  private String name;
  private String email;
  private Long meetupId;
  private String meetupTitle;
  private MeetupType meetupType;
  private byte[] signature;

  public byte[] getPlainText() {
    return (meetupType.getLabel() + meetupId + name + email).getBytes();
  }

  @Override
  @SuppressWarnings("PMD")
  public Receipt clone() {
    Receipt receipt = new Receipt();
    copyProperties(this, receipt);
    return receipt;
  }
}
