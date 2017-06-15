package gov.samhsa.c2s.pcm.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ConsentRepository extends JpaRepository<Consent, Long> {
    Optional<Consent> findOneByIdAndPatientIdAndConsentAttestationIsNullAndConsentRevocationIsNull(Long consentId,
                                                                                                   String patientId);

    Optional<Consent> findOneByIdAndPatientIdAndConsentAttestationIsNotNullAndConsentRevocationIsNull(Long consentId,
                                                                                                      String patientId);

    Optional<Consent> findOneByIdAndPatientIdAndConsentAttestationIsNotNullAndConsentRevocationIsNotNull(Long consentId, String patientId);

    Optional<Consent> findOneByIdAndPatientId(Long consentId, String patientId);

    Page<Consent> findAllByPatientId(String patientId, Pageable pageable);

    Optional<Consent>
    findOneByPatientIdAndFromProvidersContainingAndToProvidersContainingAndSharePurposesContainingAndStartDateBeforeAndEndDateAfterAndConsentAttestationNotNull(String patientId, Provider fromProvider, Provider toProvider, Purpose purpose, LocalDate startDateBefore, LocalDate endDateAfter);


}
