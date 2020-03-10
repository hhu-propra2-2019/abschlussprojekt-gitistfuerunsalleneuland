package mops.hhu.de.rheinjug1.praxis.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
public class ReceiptSignature {
  @Id private Long id;
  private String signature;
  private int used;

  public ReceiptSignature(final String signature) {
    this.signature = signature;
    this.used = 0;
  }

  @Override
  public String toString() {
    return "ReceiptSignature{"
        + "id="
        + id
        + ", signature='"
        + signature
        + '\''
        + ", used="
        + used
        + '}';
  }

  public boolean isUsed() {
    return used == 1;
  }
}
