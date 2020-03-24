package mops.hhu.de.rheinjug1.praxis.adapters.meetup.dto;

import lombok.NoArgsConstructor;
import lombok.Setter;
import mops.hhu.de.rheinjug1.praxis.annotations.DTO;

@DTO
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
