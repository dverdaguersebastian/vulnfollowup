package es.nebrija.vulnfollowup.repository;

import es.nebrija.vulnfollowup.model.entity.Asset;
import es.nebrija.vulnfollowup.model.enums.AssetStatus;
import es.nebrija.vulnfollowup.model.enums.Environment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio Spring Data JPA para la entidad Asset.
 */
@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    List<Asset> findByEnvironment(Environment environment);

    List<Asset> findByStatus(AssetStatus status);

    List<Asset> findByProviderId(Long providerId);

    List<Asset> findByType(String type);
}
