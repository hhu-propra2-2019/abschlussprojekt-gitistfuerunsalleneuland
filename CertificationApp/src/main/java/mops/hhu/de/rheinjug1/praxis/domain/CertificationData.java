package mops.hhu.de.rheinjug1.praxis.domain;

import lombok.Data;

@Data
public class CertificationData {

  private String matrikelNummer;
  private String name;
  private String anrede;
  private String date;
  private String type;
  private String[] veranstaltungen;

}
