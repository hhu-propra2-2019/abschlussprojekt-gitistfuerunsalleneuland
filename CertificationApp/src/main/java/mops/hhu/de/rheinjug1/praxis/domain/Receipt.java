package mops.hhu.de.rheinjug1.praxis.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Receipt {

	private long keycloakId;
	private long meetupId;
	private String type;
	private String signature;
	
}
