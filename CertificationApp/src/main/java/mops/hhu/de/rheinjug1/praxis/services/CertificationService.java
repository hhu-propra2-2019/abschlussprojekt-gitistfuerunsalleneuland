package mops.hhu.de.rheinjug1.praxis.services;

import mops.hhu.de.rheinjug1.praxis.domain.CertificationData;
import org.springframework.stereotype.Service;

@Service
public class CertificationService {

  public CertificationData createCertification(CertificationData certificationData) {

    return new CertificationData();
  }
}
