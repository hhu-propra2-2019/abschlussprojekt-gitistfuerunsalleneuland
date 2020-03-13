package mops.hhu.de.rheinjug1.praxis.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import mops.hhu.de.rheinjug1.praxis.domain.Receipt;

@Service
public class ReceiptService {
	
	public ArrayList<Receipt> readAll(ArrayList<MultipartFile> receiptFiles) throws IOException {
		ArrayList<Receipt> receipts = new ArrayList<Receipt>();
		for (MultipartFile receiptFile : receiptFiles) receipts.add(read(receiptFile));
		return receipts;
	}

	public Receipt read(MultipartFile receiptFile) throws IOException{
		InputStream input = receiptFile.getInputStream();
		String[] lines = getLines(input);
		if (lines.length != 3) throw new IOException();
		lines = cut(lines);
		return new Receipt((Long) null,Long.parseLong(lines[0]),lines[1],lines[2]);
	}

	private String[] cut(String[] lines) {
		lines[0] = lines[0].substring(lines[0].indexOf(':') + 2);
		lines[1] = lines[1].substring(lines[1].indexOf(':') + 2);
		return lines;
	}

	private String[] getLines(InputStream input) throws IOException {
		Scanner scanner = new Scanner(input).useDelimiter("\\A");
		String receiptString = scanner.hasNext() ? scanner.next() : "";
		receiptString = receiptString.substring(0, receiptString.lastIndexOf('\n') -1);
		System.out.println(receiptString);
		if (receiptString.matches(
		"Name: \\\\w+\\\r\n" + 
		"Veranstaltungs-ID: \\\\d+\\\r\n" + 
		"Titel: \\\\w+\\\r\n" + 
		"Typ: \\\\w+\\\r\n" + 
		"\\.+\\\r\n")) {		
		    String[] lines = (String[]) receiptString.lines().filter(line -> isNecessary(line)).toArray();
		    return lines;
		} else throw new IOException();
	}
	
	private boolean isNecessary(String line) {		
		if (line.contains("Veranstaltungs-ID: ") 
				|| line.contains("Typ: ")	
				||!line.contains(":")) {
			return true;
		} else return false;
	}

	public void verify(ArrayList<Receipt> receipts) {
		// TODO Auto-generated method stub
		
	}

}
