package mops.hhu.de.rheinjug1.praxis.services;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mops.hhu.de.rheinjug1.praxis.domain.Receipt;
import mops.hhu.de.rheinjug1.praxis.interfaces.ReceiptVerificationInterface;

@Service
public class VerificationService implements ReceiptVerificationInterface {
	
	@Autowired private KeyService keyService;
	
	public boolean verify(final Receipt receipt) {
		
		PublicKey publicKey = null;
		try {
			publicKey = keyService.getKeyPairFromKeyStore().getPublic();
		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | UnrecoverableEntryException
				| IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String name = receipt.getName();
		long meetupID = receipt.getMeetupId();
		String meetupType = receipt.getMeetupType();
		String email = receipt.getEmail();
		String signature = receipt.getSignature();
		
		String hash = hash(name, meetupID, meetupType, email);
		String compareHash = decrypt(signature);
		
		return hash.equals(compareHash);
	}

	private String decrypt(String signature) {
		// TODO Auto-generated method stub
		return null;
	}

	private String hash(String name, long meetupID, String meetupType, String email) {
		// TODO Auto-generated method stub
		return null;
	}
}
