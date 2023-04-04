package com.mvassoler.newxgracco.newxgracco.specs;

import com.mvassoler.newxgracco.newxgracco.domain.Domain;
import com.mvassoler.newxgracco.newxgracco.filters.DomainFilterDTO;
import com.mvassoler.newxgracco.newxgracco.repositories.DomainRepository;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Objects;

public class TesteSpec implements GenericSpec<Domain> {

    private final DomainRepository domainRepository;

    public TesteSpec(DomainRepository domainRepository) {
        this.domainRepository = domainRepository;
    }

    public List<Domain> listFilters(DomainFilterDTO filter) {
        return domainRepository.findAll(this.madeSpecifications(filter));
    }

    public Specification<Domain> madeSpecifications(DomainFilterDTO filter) {
        Specification<Domain> specification = null;
        specification = this.madeSpecificationName(specification, filter);
        return specification;
    }


    private Specification<Domain> madeSpecificationName(Specification<Domain> specification, DomainFilterDTO filter) {
        if (!Objects.isNull(filter.getNames()) && !filter.getNames().isEmpty()) {
            if (Objects.isNull(specification)) {
                specification = this.filterAtributeString(filter.getNames(), "name");
            } else {
                specification = specification.and(this.filterAtributeString(filter.getNames(), "name"));
            }
        }
        return specification;
    }

}
