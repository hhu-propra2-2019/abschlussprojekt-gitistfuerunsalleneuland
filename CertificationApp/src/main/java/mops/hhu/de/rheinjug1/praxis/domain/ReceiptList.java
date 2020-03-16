package mops.hhu.de.rheinjug1.praxis.domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Data
public class ReceiptList {

    private ArrayList<MultipartFile> receiptList = new ArrayList<MultipartFile>();

    public void addNewReceipt(MultipartFile newReceipt){
        System.out.println("newReceipt wird ausgeführt");
        if (newReceipt != null) {
            receiptList.add(newReceipt);
        }
    }
}
