package mops.hhu.de.rheinjug1.praxis.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
public class ReceiptHash {
  @Id private Long id;
  private String hash;
  private int used;

  @Override
  public String toString() {
    return "Receipt{" + "id=" + id + ", hash='" + hash + '\'' + ", used=" + used + '}';
  }

  public boolean isUsed() {
    return used == 1;
  }
}
