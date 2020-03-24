package mops.hhu.de.rheinjug1.praxis.adapters.meetup.dto;

import lombok.NoArgsConstructor;
import lombok.Setter;
import mops.hhu.de.rheinjug1.praxis.annotations.DTO;

@SuppressWarnings({"PMD.FieldNamingConventions", "PMD.TooManyFields"})
@DTO
@Setter
@NoArgsConstructor
public class VenueResponseDTO {
  private long id;
  private String name;
  private double lat;
  private double lon;
  private boolean repinned;
  private String address_1;
  private String city;
  private String country;
  private String localized_country_name;
}
