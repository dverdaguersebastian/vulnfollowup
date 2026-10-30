package es.nebrija.vulnfollowup.repository;

import es.nebrija.vulnfollowup.model.entity.TicProvider;
import es.nebrija.vulnfollowup.model.enums.Criticality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio Spring Data JPA para la entidad TicProvider (Marco DORA).
 */
@Repository
public interface TicProviderRepository extends JpaRepository<TicProvider, Long> {

    Optional<TicProvider> findByName(String name);

    boolean existsByName(String name);

    List<TicProvider> findByCriticality(Criticality criticality);

    List<TicProvider> findByIsActiveTrue();

    /**
     * Localiza proveedores cuya fecha de revisión contractual sea anterior a una fecha dada
     * (requerimiento de auditoría y gobernanza DORA).
     */
    List<TicProvider> findByContractReviewDateBeforeAndIsActiveTrue(LocalDate date);
}
