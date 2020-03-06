package mops.hhu.de.rheinjug1.praxis.clients.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Fee {
  private String accepts;
  private double amount;
  private String currency;
  private String description;
  private String label;
  private boolean required;
}
