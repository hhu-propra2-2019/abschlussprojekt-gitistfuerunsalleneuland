package mops.hhu.de.rheinjug1.praxis.domain;

import java.util.ArrayList;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ReceiptForm {
	
	private String matrikelNummer;
	private ArrayList<MultipartFile> receiptList = new ArrayList<MultipartFile>();
	private MultipartFile newReceipt;
	
	public void addReceipt() {
		if (newReceipt != null) receiptList.add(newReceipt);
	}

}
