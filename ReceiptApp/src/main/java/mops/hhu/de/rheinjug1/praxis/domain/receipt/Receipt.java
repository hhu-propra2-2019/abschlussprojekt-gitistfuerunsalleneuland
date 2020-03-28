package mops.hhu.de.rheinjug1.praxis.domain.receipt;

import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Receipt implements Cloneable {

  private String name;
  private String email;
  private Long meetupId;
  private String meetupTitle;
  private MeetupType meetupType;
  private byte[] plainText;
  private byte[] signature;

  public static Receipt createFromDTO(final ReceiptDTO dto) throws IOException {
    return Receipt.builder()
        .name(dto.getName())
        .email(dto.getEmail())
        .meetupId(dto.getMeetupId())
        .meetupTitle(dto.getMeetupTitle())
        .meetupType(dto.getMeetupType())
        .plainText(
            (dto.getMeetupType().getLabel() + dto.getMeetupId() + dto.getName() + dto.getEmail())
                .getBytes())
        .signature(dto.getSignature())
        .build();
  }

  @Override
  @SuppressWarnings("PMD")
  public Receipt clone() {
    return Receipt.builder()
        .name(name)
        .email(email)
        .meetupId(meetupId)
        .meetupTitle(meetupTitle)
        .meetupType(meetupType)
        .plainText(plainText)
        .signature(signature)
        .build();
  }

  public ReceiptDTO toDTO() {
    return ReceiptDTO.builder()
        .email(email)
        .meetupId(meetupId)
        .meetupTitle(meetupTitle)
        .meetupType(meetupType)
        .name(name)
        .signature(signature)
        .build();
  }
}
