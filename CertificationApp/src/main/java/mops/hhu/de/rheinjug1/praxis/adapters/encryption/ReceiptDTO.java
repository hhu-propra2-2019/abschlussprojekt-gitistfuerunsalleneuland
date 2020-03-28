package mops.hhu.de.rheinjug1.praxis.adapters.encryption;

import static org.springframework.beans.BeanUtils.copyProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.entities.Receipt;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiptDTO {

  private String name;
  private String email;
  private Long meetupId;
  private String meetupTitle;
  private MeetupType meetupType;
  private byte[] signature;

  public ReceiptDTO(final Receipt receipt) {
    copyProperties(receipt, this);
  }

  public Receipt toReceipt() {
    return Receipt.builder()
        .name(name)
        .email(email)
        .meetupId(meetupId)
        .meetupTitle(meetupTitle)
        .signature(signature)
        .meetupType(meetupType)
        .build();
  }
}
