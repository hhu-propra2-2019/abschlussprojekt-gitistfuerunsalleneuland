package mops.hhu.de.rheinjug1.praxis.domain;

import lombok.Data;

@Data
public class Receipt {

	private long keycloakId;
	private long meetupId;
	private String type;
	private String signature;
	
}
