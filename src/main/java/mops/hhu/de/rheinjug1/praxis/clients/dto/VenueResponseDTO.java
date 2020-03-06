package mops.hhu.de.rheinjug1.praxis.clients.dto;

import lombok.NoArgsConstructor;
import lombok.Setter;
import mops.hhu.de.rheinjug1.praxis.database.entities.Venue;

@SuppressWarnings({"PMD.FieldNamingConventions", "PMD.TooManyFields"})
@Setter
@NoArgsConstructor
public class VenueResponseDTO {
  private int id;
  private String name;
  private double lat;
  private double lon;
  private boolean repinned;
  private String address_1;
  private String city;
  private String country;
  private String localized_country_name;

  public Venue toVenue() {
    return new Venue(this.name, this.address_1, this.city);
  };
}
