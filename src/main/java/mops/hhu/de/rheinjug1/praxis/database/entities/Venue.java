package mops.hhu.de.rheinjug1.praxis.database.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Getter
@ToString
public class Venue {
  @Id Long id;
  private String name;
  private String address;
  private String city;

  public Venue(String name, String address, String city) {
    this.name = name;
    this.address = address;
    this.city = city;
  }
}
