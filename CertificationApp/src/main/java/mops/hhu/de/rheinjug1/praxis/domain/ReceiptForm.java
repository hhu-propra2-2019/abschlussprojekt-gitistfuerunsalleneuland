package mops.hhu.de.rheinjug1.praxis.domain;

import java.util.ArrayList;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ReceiptForm {
	
	private String matrikelNummer;
	private MultipartFile newReceipt;


}
