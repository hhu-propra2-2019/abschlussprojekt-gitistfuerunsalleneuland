package mops.hhu.de.rheinjug1.praxis.clients.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings({"PMD.FieldNamingConventions", "PMD.TooManyFields"})
@Getter
@Setter
@NoArgsConstructor
public class Venue {
  private int id;
  private String name;
  private double lat;
  private double lon;
  private boolean repinned;
  private String address_1;
  private String city;
  private String country;
  private String localized_country_name;
}
