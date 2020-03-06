package mops.hhu.de.rheinjug1.praxis.database.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Venue {
  private String name;
  private String address;
  private String city;
}
