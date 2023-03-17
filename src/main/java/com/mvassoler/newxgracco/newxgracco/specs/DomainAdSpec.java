package com.mvassoler.newxgracco.newxgracco.specs;

import com.mvassoler.newxgracco.newxgracco.domain.DomainAd;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public class DomainAdSpec {

    private static final String LITERAL_DOMAIN = "domain";

    private DomainAdSpec() {
    }

    public static Specification<DomainAd> filterLdapBaseDn(List<String> ldapBaseDns) {
        if (ldapBaseDns.size() > 1) {
            return DomainAdSpec.filterLdapBaseDnList(ldapBaseDns);
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + ldapBaseDns.get(0) + "%");
    }

    public static Specification<DomainAd> filterLdapUrls(List<String> ldapUrls) {
        if (ldapUrls.size() > 1) {
            return DomainAdSpec.filterLdapUrlsList(ldapUrls);
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + ldapUrls.get(0) + "%");
    }

    public static Specification<DomainAd> filterLdapUserNames(List<String> ldapUserNames) {
        if (ldapUserNames.size() > 1) {
            return DomainAdSpec.filterLdapUserNamesList(ldapUserNames);
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + ldapUserNames.get(0) + "%");
    }

    public static Specification<DomainAd> filterLdapDomainIdsList(List<UUID> domainIds) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(LITERAL_DOMAIN).get("id")).value(domainIds);
    }

    public static Specification<DomainAd> filterLdapDomainIdentificationName(List<String> domainIdentificationNames) {
        if (domainIdentificationNames.size() > 1) {
            return DomainAdSpec.filterLdapDomainIdentificationNamesList(domainIdentificationNames);
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(LITERAL_DOMAIN).get("identificationName"), "%" + domainIdentificationNames.get(0) + "%");
    }

    public static Specification<DomainAd> filterLdapDomainIdentificationNumbers(List<String> domainIdentificationNumbers) {
        if (domainIdentificationNumbers.size() > 1) {
            return DomainAdSpec.filterLdapDomainIdentificationNumbersList(domainIdentificationNumbers);
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(LITERAL_DOMAIN).get("identificationNumber"), "%" + domainIdentificationNumbers.get(0) + "%");
    }


    private static Specification<DomainAd> filterLdapBaseDnList(List<String> ldapBaseDns) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("ldapBaseDn")).value(ldapBaseDns);
    }

    private static Specification<DomainAd> filterLdapUrlsList(List<String> ldapUrls) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("ldapUrl")).value(ldapUrls);
    }

    private static Specification<DomainAd> filterLdapUserNamesList(List<String> ldapUserNames) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("ldapUserName")).value(ldapUserNames);
    }

    private static Specification<DomainAd> filterLdapDomainIdentificationNamesList(List<String> domainIdentificationNames) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(LITERAL_DOMAIN).get("identificationName")).value(domainIdentificationNames);
    }

    private static Specification<DomainAd> filterLdapDomainIdentificationNumbersList(List<String> domainIdentificationNumbers) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(LITERAL_DOMAIN).get("identificationNumber")).value(domainIdentificationNumbers);
    }


}
