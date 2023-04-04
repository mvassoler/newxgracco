package com.mvassoler.newxgracco.newxgracco.queries;

import com.mvassoler.newxgracco.newxgracco.domain.Domain;
import com.mvassoler.newxgracco.newxgracco.filters.DomainFilterDTO;
import com.mvassoler.newxgracco.newxgracco.repositories.DomainRepository;
import com.mvassoler.newxgracco.newxgracco.specs.DomainSpec;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Repository
public class DomainRepositoryQueriesImpl implements RepositoryQueries<Domain, DomainFilterDTO, DomainRepository> {

    private final DomainRepository domainRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public DomainRepositoryQueriesImpl(DomainRepository domainRepository) {
        this.domainRepository = domainRepository;
    }

    @Override
    public List<Domain> listFilters(DomainFilterDTO filter) {
        return domainRepository.findAll(this.madeSpecifications(filter));
    }

    @Override
    public Page<Domain> listFilters(DomainFilterDTO filter, Pageable pageable) {
        return domainRepository.findAll(this.madeSpecifications(filter), pageable);
    }

    @Override
    public Specification<Domain> madeSpecifications(DomainFilterDTO filter) {
        Specification<Domain> specification = null;
        specification = this.madeSpecificationName(specification, filter);
        specification = this.madeSpecificationIdentificationNumber(specification, filter);
        specification = this.madeSpecificationEnable(specification, filter);
        specification = this.madeSpecificationPersonalType(specification, filter);
        return specification;
    }


    private Specification<Domain> madeSpecificationName(Specification<Domain> specification, DomainFilterDTO filter) {
        if (!Objects.isNull(filter.getNames()) && !filter.getNames().isEmpty()) {
            if (Objects.isNull(specification)) {
                specification = DomainSpec.filterName(filter.getNames());
            } else {
                specification = specification.and(DomainSpec.filterName(filter.getNames()));
            }
        }
        return specification;
    }

    private Specification<Domain> madeSpecificationIdentificationNumber(Specification<Domain> specification, DomainFilterDTO filter) {
        if (!Objects.isNull(filter.getIdentificationNumbers()) && !filter.getIdentificationNumbers().isEmpty()) {
            if (Objects.isNull(specification)) {
                specification = DomainSpec.filterIdentificationNumber(filter.getIdentificationNumbers());
            } else {
                specification = specification.and(DomainSpec.filterIdentificationNumber(filter.getIdentificationNumbers()));
            }
        }
        return specification;
    }

    private Specification<Domain> madeSpecificationEnable(Specification<Domain> specification, DomainFilterDTO filter) {
        if (!Objects.isNull(filter.getEnabled())) {
            if (Objects.isNull(specification)) {
                specification = DomainSpec.filterEnabled(filter.getEnabled());
            } else {
                specification = specification.and(DomainSpec.filterEnabled(filter.getEnabled()));
            }
        }
        return specification;
    }

    private Specification<Domain> madeSpecificationPersonalType(Specification<Domain> specification, DomainFilterDTO filter) {
        if (!Objects.isNull(filter.getPersonalType())) {
            if (Objects.isNull(specification)) {
                specification = DomainSpec.filterPersonalType(filter.getPersonalType());
            } else {
                specification = specification.and(DomainSpec.filterPersonalType(filter.getPersonalType()));
            }
        }
        return specification;
    }

    @Override
    public DomainRepository getRepository() {
        return this.domainRepository;
    }

    public Domain selectJpqlByUuid(UUID id) {
        TypedQuery<Domain> typedQuery = entityManager.createQuery(
                "select d from Domain d where d.id = :id", Domain.class
        );
        typedQuery.setParameter("id", id);
        return typedQuery.getSingleResult();
    }
}
