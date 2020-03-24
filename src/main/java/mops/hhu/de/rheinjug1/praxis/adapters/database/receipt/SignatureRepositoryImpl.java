package mops.hhu.de.rheinjug1.praxis.adapters.database.receipt;

import mops.hhu.de.rheinjug1.praxis.adapters.database.DrivenAdapter;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.SignatureRecord;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.SignatureRepository;

public class SignatureRepositoryImpl extends DrivenAdapter<SignatureRecord, SignatureDTO> implements SignatureRepository {
    private final SignatureBackendRepo signatureBackendRepo;

    public SignatureRepositoryImpl(SignatureBackendRepo signatureBackendRepo) {
        this.signatureBackendRepo = signatureBackendRepo;
        this.entity = new SignatureRecord();
        this.dto = new SignatureDTO();
    }

    @Override
    public int countSignatureByMeetupType(String meetupType) {
        return signatureBackendRepo.countSignatureByMeetupType(meetupType);
    }

    @Override
    public void save(SignatureRecord signatureRecord) {
        signatureBackendRepo.save(toDTO(signatureRecord));
    }
}
