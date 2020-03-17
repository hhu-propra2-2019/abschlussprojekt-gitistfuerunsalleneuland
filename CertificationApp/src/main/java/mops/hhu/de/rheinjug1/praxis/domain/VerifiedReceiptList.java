package mops.hhu.de.rheinjug1.praxis.domain;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class VerifiedReceiptList {

    private List<Receipt> receiptList = new ArrayList<>();
    private List<String> signatures = new ArrayList<>();

    public void addNewReceipt(Receipt newReceipt){
    	if (isDuplicateSignature(newReceipt.getSignature())) {  	
    		System.out.println("duplikate signatures");
    	} else {
          receiptList.add(newReceipt);
          signatures.add(newReceipt.getSignature());
    	}
    }
    private boolean isDuplicateSignature(String newSignature) {
    	for (String signature : signatures) {
    		if (signature.equals(newSignature)) {
    			return true;
    		}
    	}
    	return false;
    }
    
}