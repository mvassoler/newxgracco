package com.mvassoler.newxgracco.newxgracco.queries;

import com.mvassoler.newxgracco.newxgracco.domain.DomainAd;
import com.mvassoler.newxgracco.newxgracco.filters.DomainAdFilterDTO;
import com.mvassoler.newxgracco.newxgracco.repositories.DomainAdRepository;
import com.mvassoler.newxgracco.newxgracco.specs.DomainAdSpec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class DomainAdRepositoryQueriesImpl implements RepositoryQueries<DomainAd, DomainAdFilterDTO, DomainAdRepository> {

    private final DomainAdRepository domainAdRepository;

    public DomainAdRepositoryQueriesImpl(DomainAdRepository domainAdRepository) {
        this.domainAdRepository = domainAdRepository;
    }

    @Override
    public List<DomainAd> listFilters(DomainAdFilterDTO filter) {
        return domainAdRepository.findAll(this.madeSpecifications(filter));
    }

    @Override
    public Page<DomainAd> listFilters(DomainAdFilterDTO filter, Pageable pageable) {
        return domainAdRepository.findAll(this.madeSpecifications(filter), pageable);
    }

    @Override
    public Specification<DomainAd> madeSpecifications(DomainAdFilterDTO filter) {
        Specification<DomainAd> specification = null;
        specification = this.madeSpecificationLdapBaseDns(specification, filter);
        specification = this.madeSpecificationLdapUrls(specification, filter);
        specification = this.madeSpecificationLdapUserNames(specification, filter);
        specification = this.madeSpecificationLdapDomainIds(specification, filter);
        specification = this.madeSpecificationLdapDomainIdentificationNames(specification, filter);
        specification = this.madeSpecificationLdapDomainIdentificationNumbers(specification, filter);
        return specification;
    }

    private Specification<DomainAd> madeSpecificationLdapBaseDns(Specification<DomainAd> specification, DomainAdFilterDTO filter) {
        if (!Objects.isNull(filter.getLdapBaseDns()) && !filter.getLdapBaseDns().isEmpty()) {
            if (Objects.isNull(specification)) {
                specification = DomainAdSpec.filterLdapBaseDn(filter.getLdapBaseDns());
            } else {
                specification = specification.and(DomainAdSpec.filterLdapBaseDn(filter.getLdapBaseDns()));
            }
        }
        return specification;
    }

    private Specification<DomainAd> madeSpecificationLdapUrls(Specification<DomainAd> specification, DomainAdFilterDTO filter) {
        if (!Objects.isNull(filter.getLdapUrls()) && !filter.getLdapUrls().isEmpty()) {
            if (Objects.isNull(specification)) {
                specification = DomainAdSpec.filterLdapUrls(filter.getLdapUrls());
            } else {
                specification = specification.and(DomainAdSpec.filterLdapUrls(filter.getLdapUrls()));
            }
        }
        return specification;
    }

    private Specification<DomainAd> madeSpecificationLdapUserNames(Specification<DomainAd> specification, DomainAdFilterDTO filter) {
        if (!Objects.isNull(filter.getLdapUserNames()) && !filter.getLdapUserNames().isEmpty()) {
            if (Objects.isNull(specification)) {
                specification = DomainAdSpec.filterLdapUserNames(filter.getLdapUserNames());
            } else {
                specification = specification.and(DomainAdSpec.filterLdapUserNames(filter.getLdapUserNames()));
            }
        }
        return specification;
    }

    private Specification<DomainAd> madeSpecificationLdapDomainIds(Specification<DomainAd> specification, DomainAdFilterDTO filter) {
        if (!Objects.isNull(filter.getDomainIds()) && !filter.getDomainIds().isEmpty()) {
            if (Objects.isNull(specification)) {
                specification = DomainAdSpec.filterLdapDomainIdsList(filter.getDomainIds());
            } else {
                specification = specification.and(DomainAdSpec.filterLdapDomainIdsList(filter.getDomainIds()));
            }
        }
        return specification;
    }

    private Specification<DomainAd> madeSpecificationLdapDomainIdentificationNames(Specification<DomainAd> specification, DomainAdFilterDTO filter) {
        if (!Objects.isNull(filter.getDomainIdentificationNames()) && !filter.getDomainIdentificationNames().isEmpty()) {
            if (Objects.isNull(specification)) {
                specification = DomainAdSpec.filterLdapDomainIdentificationName(filter.getDomainIdentificationNames());
            } else {
                specification = specification.and(DomainAdSpec.filterLdapDomainIdentificationName(filter.getDomainIdentificationNames()));
            }
        }
        return specification;
    }

    private Specification<DomainAd> madeSpecificationLdapDomainIdentificationNumbers(Specification<DomainAd> specification, DomainAdFilterDTO filter) {
        if (!Objects.isNull(filter.getDomainIdentificationNumbers()) && !filter.getDomainIdentificationNumbers().isEmpty()) {
            if (Objects.isNull(specification)) {
                specification = DomainAdSpec.filterLdapDomainIdentificationNumbers(filter.getDomainIdentificationNumbers());
            } else {
                specification = specification.and(DomainAdSpec.filterLdapDomainIdentificationNumbers(filter.getDomainIdentificationNumbers()));
            }
        }
        return specification;
    }

    @Override
    public DomainAdRepository getRepository() {
        return this.domainAdRepository;
    }
}
