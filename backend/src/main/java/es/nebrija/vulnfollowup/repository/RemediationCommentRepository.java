package es.nebrija.vulnfollowup.repository;

import es.nebrija.vulnfollowup.model.entity.RemediationComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio Spring Data JPA para la entidad RemediationComment.
 */
@Repository
public interface RemediationCommentRepository extends JpaRepository<RemediationComment, Long> {

    List<RemediationComment> findByVulnerabilityIdOrderByCreatedAtDesc(Long vulnerabilityId);

    List<RemediationComment> findByUserIdOrderByCreatedAtDesc(Long userId);
}
