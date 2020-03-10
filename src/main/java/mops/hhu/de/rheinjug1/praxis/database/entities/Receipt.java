package mops.hhu.de.rheinjug1.praxis.database.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Receipt {
  @Id private Long id;
  private int used;

  public Receipt() {}

  @Override
  public String toString() {
    return "Receipt{" + "id=" + id + ", used=" + used + '}';
  }

  public boolean isUsed() {
    return used == 1;
  }

  public Receipt(final boolean used) {
    this.used = used ? 1 : 0;
  }
}