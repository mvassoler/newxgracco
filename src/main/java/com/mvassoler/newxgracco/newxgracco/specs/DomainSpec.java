package com.mvassoler.newxgracco.newxgracco.specs;

import com.mvassoler.newxgracco.newxgracco.domain.Domain;
import com.mvassoler.newxgracco.newxgracco.enums.PersonalType;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class DomainSpec {

    private DomainSpec() {
    }

    public static Specification<Domain> filterName(List<String> names) {
        if (names.size() > 1) {
            return DomainSpec.filterNameList(names);
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + names.get(0) + "%");
    }

    public static Specification<Domain> filterIdentificationNumber(List<String> identificationNumber) {
        if (identificationNumber.size() > 1) {
            return DomainSpec.filterIdentificationNumberList(identificationNumber);
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("identificationNumber"), "%" + identificationNumber.get(0) + "%");
    }

    public static Specification<Domain> filterEnabled(Boolean enabled) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("enabled"), enabled);
    }

    public static Specification<Domain> filterPersonalType(PersonalType personalType) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("personalType"), personalType);
    }

    public static Specification<Domain> filterNameList(List<String> names) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("name")).value(names);
    }

    private static Specification<Domain> filterIdentificationNumberList(List<String> identificationNumber) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("identificationNumber")).value(identificationNumber);
    }

}
