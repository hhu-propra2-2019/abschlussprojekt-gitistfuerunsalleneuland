package mops.hhu.de.rheinjug1.praxis.domain.receipt;

public interface SignatureRepository {
    int countSignatureByMeetupType(String meetupType);
    void save(SignatureRecord signatureRecord);
}
