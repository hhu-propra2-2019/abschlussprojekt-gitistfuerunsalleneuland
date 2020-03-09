package mops.hhu.de.rheinjug1.praxis.database.entities;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Getter
@ToString
public class Venue {
  @Id Long id;
  private final String name;
  private final String address;
  private final String city;

  public Venue(final String name, final String address, final String city) {
    this.name = name;
    this.address = address;
    this.city = city;
  }
}
