package mops.hhu.de.rheinjug1.praxis.clients.dto;

import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
public class FeeResponseDTO {
  private String accepts;
  private double amount;
  private String currency;
  private String description;
  private String label;
  private boolean required;
}
