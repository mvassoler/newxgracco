package com.mvassoler.newxgracco.newxgracco.repositories;

import com.mvassoler.newxgracco.newxgracco.domain.Domain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DomainRepository extends CustomJpaRepository<Domain, UUID>, JpaSpecificationExecutor<Domain> {

    @Query(" from Domain s where s.deleted is null")
    Page<Domain> findAllGeneral(Pageable pageable);

    Optional<Domain> findByIdentificationNumber(String identificationNumber);

    List<Domain> findAllByDeletedIsNull();

    Optional<Domain> findByIdentificationNameAndDeletedIsNull(String identificationName);
}
