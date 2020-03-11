package mops.hhu.de.rheinjug1.praxis.services;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;

import lombok.NoArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.entities.Receipt;

@NoArgsConstructor
public class CertificationService {
	
	
	public void requestCertification(Receipt ... receipts) {
		if (receipts == null) return;
		if (receipts.length == 0) return;
		List<Receipt> receiptList = Arrays.asList(receipts);
		receiptList = getVerifyReceipts(receiptList);
		List<Receipt> rheinjugReceipts = getRheinjugReceipts(receiptList);
		List<Receipt> entwickelbarReceipts = getEntwickelbarReceipts(receiptList);
		removeRedundantReceipts(rheinjugReceipts);
		try {
			notifyAccountable(rheinjugReceipts, entwickelbarReceipts);
		} catch (Exception e) {
			notifyThatRequestFailed();
			return;
		}
		for (Receipt receipt : rheinjugReceipts) receipt.setUsed(1);
		for (Receipt receipt : entwickelbarReceipts) receipt.setUsed(1);	
	}

	private void notifyThatRequestFailed() {
		// TODO Auto-generated method stub		
	}

	private List<Receipt> getEntwickelbarReceipts(List<Receipt> receiptList) {
		//filter out Entwickelbar-Receipts
		return receiptList;
	}

	private List<Receipt> getRheinjugReceipts(List<Receipt> receiptList) {
		//filter out Rheinjug-Receipts
		return receiptList;
	}
	
	private void notifyAccountable(List<Receipt> rheinjugReceipts, List<Receipt> entwickelbarReceipts) {
		// TODO email/web notification for the chief to print a Certificate		
	}
	
	private void removeRedundantReceipts(List<Receipt> rheinjugReceipts) {
		while (rheinjugReceipts.size() % 3 != 0) rheinjugReceipts.remove(0);
	}


	private List<Receipt> getVerifyReceipts(List<Receipt> receiptList) {
		receiptList = receiptList.stream().filter(unusedReceipt -> !unusedReceipt.isUsed()).collect(toList());
		return receiptList;
	}

	public int amountCertifications(Receipt ... receipts) {
		if (receipts == null) return 0;
		if (receipts.length == 0) return 0;
		List<Receipt> receiptList = Arrays.asList(receipts);
		receiptList = getVerifyReceipts(receiptList);
		List<Receipt> rheinjugReceipts = getRheinjugReceipts(receiptList);
		List<Receipt> entwickelbarReceipts = getEntwickelbarReceipts(receiptList);
		return (rheinjugReceipts.size() % 3 + entwickelbarReceipts.size());
		
	}
	


}
