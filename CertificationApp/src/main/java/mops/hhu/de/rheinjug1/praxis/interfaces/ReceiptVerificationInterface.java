package mops.hhu.de.rheinjug1.praxis.interfaces;

import mops.hhu.de.rheinjug1.praxis.domain.Receipt;

public interface ReceiptVerificationInterface {

	boolean verify(Receipt receipt);
}
