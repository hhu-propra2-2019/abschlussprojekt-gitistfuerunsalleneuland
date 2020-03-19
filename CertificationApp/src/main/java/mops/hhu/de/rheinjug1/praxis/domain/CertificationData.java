package mops.hhu.de.rheinjug1.praxis.domain;

import lombok.Data;

@Data
public class CertificationData {

  private String matrikelNummer;
  private String vorname;
  private String name;
  private String anrede;
  private String date;
  private String type;
  private String[] veranstaltungen;

  private Receipt firstRheinjugReceipt;
  private Receipt seccondRheinjugReceipt;
  private Receipt thirdRheinjugReceipt;
  private Receipt entwickelbarReceipt;
}
