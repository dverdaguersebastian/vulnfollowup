package es.nebrija.vulnfollowup.repository;

import es.nebrija.vulnfollowup.model.entity.CvssAssessment;
import es.nebrija.vulnfollowup.model.enums.Severity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repositorio Spring Data JPA para la entidad CvssAssessment.
 */
@Repository
public interface CvssAssessmentRepository extends JpaRepository<CvssAssessment, Long> {

    List<CvssAssessment> findByVulnerabilityId(Long vulnerabilityId);

    List<CvssAssessment> findBySeverity(Severity severity);

    List<CvssAssessment> findByBaseScoreBetween(BigDecimal minScore, BigDecimal maxScore);

    List<CvssAssessment> findByVersion(String version);
}
