package mops.hhu.de.rheinjug1.praxis.services;

import org.springframework.stereotype.Service;
import mops.hhu.de.rheinjug1.praxis.domain.Receipt;
import mops.hhu.de.rheinjug1.praxis.interfaces.ReceiptVerificationInterface;

@Service
public class VerificationService implements ReceiptVerificationInterface {

	public boolean verify(Receipt receipt) {
		//
		return true;
	}
	
}
