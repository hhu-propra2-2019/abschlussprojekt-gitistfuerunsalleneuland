package mops.hhu.de.rheinjug1.praxis;

import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.Mockito;
import mops.hhu.de.rheinjug1.praxis.services.CertificationService;

public class CertificationServiceTest {
	
	private CertificationService certService = new CertificationService();
	
	@Test
	public void zeroReceipts() {
		assertEquals(
				"certificate with 0 receipts",
				certService.amountCertifications(),
				0);
	}

	//unfinished
}
