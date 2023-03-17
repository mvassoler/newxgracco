package com.mvassoler.newxgracco.newxgracco.repositories;

import com.mvassoler.newxgracco.newxgracco.domain.DomainAd;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DomainAdRepository extends CustomJpaRepository<DomainAd, UUID>, JpaSpecificationExecutor<DomainAd> {

    @Query(" from DomainAd da where da.domain.deleted is null")
    Page<DomainAd> findAllGeneral(Pageable pageable);

    Optional<DomainAd> findByLdapUserNameAndLdapPasswordAndDomainDeletedIsNull(String userName, String password);

    Optional<DomainAd> findByDomainIdAndDomainDeletedIsNull(UUID domaindId);

    Optional<DomainAd> findByLdapBaseDnAndDomainDeletedIsNull(String ldapBaseDn);

    List<DomainAd> findAllByDomainDeletedIsNull();
}
