package mops.hhu.de.rheinjug1.praxis.models;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Talk {

  private LocalDateTime date;
  private int participant;
}
