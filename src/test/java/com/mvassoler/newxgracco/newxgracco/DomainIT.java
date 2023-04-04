package com.mvassoler.newxgracco.newxgracco;

import com.mvassoler.newxgracco.newxgracco.domain.Domain;
import com.mvassoler.newxgracco.newxgracco.enums.AuthenticationProvider;
import com.mvassoler.newxgracco.newxgracco.enums.PersonalType;
import com.mvassoler.newxgracco.newxgracco.filters.DomainFilterDTO;
import com.mvassoler.newxgracco.newxgracco.queries.DomainAdRepositoryQueriesImpl;
import com.mvassoler.newxgracco.newxgracco.queries.DomainRepositoryQueriesImpl;
import com.mvassoler.newxgracco.newxgracco.queries.UserRepositoryQueriesImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
class DomainIT {

    private static final String NAME = "Guacel Celulose Eucalipto Ltda";

    @Autowired
    private DomainRepositoryQueriesImpl domainRepositoryQueries;
    @Autowired
    private UserRepositoryQueriesImpl userRepositoryQueries;
    @Autowired
    private DomainAdRepositoryQueriesImpl domainAdRepositoryQueries;

    @BeforeEach
    public void setUp() {
        domainAdRepositoryQueries.getRepository().deleteAll();
        domainRepositoryQueries.getRepository().deleteAll();
        userRepositoryQueries.getRepository().deleteAll();
    }

    @Test
    void insertDomain() {
        UUID id = saveDomain(newDomain(null, "guacel@gmail.com", AuthenticationProvider.DATABASE, Boolean.TRUE, "14998889977",
                "1432333344", NAME, "GUACEL", "45885834000160", PersonalType.J));
        Assertions.assertNotNull(id);
    }

    @Test
    void findByIdentificationNumber() {
        saveDomain(newDomain(null, "guacel@gmail.com", AuthenticationProvider.DATABASE, Boolean.TRUE, "14998889977",
                "1432333344", NAME, "GUACEL", "45885834000160", PersonalType.J));
        Optional<Domain> domainSaved = domainRepositoryQueries.getRepository().findByIdentificationNumber("45885834000160");
        Assertions.assertNotNull(domainSaved);
        Assertions.assertEquals(NAME, domainSaved.orElseThrow().getName());
    }


    @Test
    void findByIdentificationNumberNotFound() {
        saveDomain(newDomain(null, "guacel@gmail.com", AuthenticationProvider.DATABASE, Boolean.TRUE, "14998889977",
                "1432333344", NAME, "GUACEL", "45885834000160", PersonalType.J));
        Optional<Domain> domainSaved = domainRepositoryQueries.getRepository().findByIdentificationNumber("45885834000161");
        Assertions.assertEquals(Boolean.FALSE, domainSaved.isPresent());
    }

    @Test
    void findByIdentificationName() {
        saveDomain(newDomain(null, "guacel@gmail.com", AuthenticationProvider.DATABASE, Boolean.TRUE, "14998889977",
                "1432333344", NAME, "GUACEL", "45885834000160", PersonalType.J));
        Optional<Domain> domainSaved = domainRepositoryQueries.getRepository().findByIdentificationNameAndDeletedIsNull("GUACEL");
        Assertions.assertNotNull(domainSaved);
        Assertions.assertEquals(NAME, domainSaved.orElseThrow().getName());
    }

    @Test
    void findByIdentificationNameNotFound() {
        saveDomain(newDomain(null, "guacel@gmail.com", AuthenticationProvider.DATABASE, Boolean.TRUE, "14998889977",
                "1432333344", NAME, "GUACEL", "45885834000160", PersonalType.J));
        Optional<Domain> domainSaved = domainRepositoryQueries.getRepository().findByIdentificationNameAndDeletedIsNull("GUACELLL");
        Assertions.assertEquals(Boolean.FALSE, domainSaved.isPresent());
    }

    @Test
    void findById() {
        UUID id = saveDomain(newDomain(null, "guacel@gmail.com", AuthenticationProvider.DATABASE, Boolean.TRUE, "14998889977",
                "1432333344", NAME, "GUACEL", "45885834000160", PersonalType.J));
        Optional<Domain> domainSaved = domainRepositoryQueries.getRepository().findById(id);
        Assertions.assertNotNull(domainSaved);
        Assertions.assertEquals(NAME, domainSaved.orElseThrow().getName());
    }

    @Test
    void findByIdNotFound() {
        saveDomain(newDomain(null, "guacel@gmail.com", AuthenticationProvider.DATABASE, Boolean.TRUE, "14998889977",
                "1432333344", NAME, "GUACEL", "45885834000160", PersonalType.J));
        Optional<Domain> domainSaved = domainRepositoryQueries.getRepository().findById(UUID.randomUUID());
        Assertions.assertEquals(Boolean.FALSE, domainSaved.isPresent());
    }

    @Test
    void findAllPageNotDeleted() {
        this.saveListDomainsPersonalTypeJ();
        Page<Domain> domains = domainRepositoryQueries.getRepository().findAllGeneral(createSearchPageRequest(0, 10));
        int numberUsers = 2;
        Assertions.assertNotNull(domains);
        Assertions.assertEquals(numberUsers, domains.getContent().size());
    }

    @Test
    void findAllListNotDeleted() {
        this.saveListDomainsPersonalTypeJ();
        List<Domain> domains = domainRepositoryQueries.getRepository().findAllByDeletedIsNull();
        int numberUsers = 2;
        Assertions.assertNotNull(domains);
        Assertions.assertEquals(numberUsers, domains.size());
    }

    @Test
    void findListSpecByNames() {
        saveListDomainsPersonalTypeJ();
        saveListDomainsPersonalTypeF();
        List<String> names = new ArrayList<>();
        names.add("Nurcel Celulose Eucalipto Ltda");
        names.add("Fulano Paulista");
        names.add("Varcel Celulose Eucalipto Ltda");
        DomainFilterDTO filter = DomainFilterDTO.builder()
                .names(names)
                .identificationNumbers(null)
                .personalType(null)
                .enabled(null)
                .build();
        List<Domain> domains = domainRepositoryQueries.listFilters(filter);
        int numberUsers = 3;
        Assertions.assertNotNull(domains);
        Assertions.assertEquals(numberUsers, domains.size());
    }

    @Test
    void findPageSpecByNames() {
        saveListDomainsPersonalTypeJ();
        saveListDomainsPersonalTypeF();
        List<String> names = new ArrayList<>();
        names.add("Nurcel Celulose Eucalipto Ltda");
        names.add("Fulano Paulista");
        names.add("Varcel Celulose Eucalipto Ltda");
        DomainFilterDTO filter = DomainFilterDTO.builder()
                .names(names)
                .identificationNumbers(null)
                .personalType(null)
                .enabled(null)
                .build();
        Page<Domain> domains = domainRepositoryQueries.listFilters(filter, createSearchPageRequest(0, 10));
        int numberUsers = 3;
        Assertions.assertNotNull(domains);
        Assertions.assertEquals(numberUsers, domains.getContent().size());
    }

    @Test
    void findListSpecByNamesLike() {
        saveListDomainsPersonalTypeJ();
        saveListDomainsPersonalTypeF();
        List<String> names = new ArrayList<>();
        names.add("Celulose");
        DomainFilterDTO filter = DomainFilterDTO.builder()
                .names(names)
                .identificationNumbers(null)
                .personalType(null)
                .enabled(null)
                .build();
        List<Domain> domains = domainRepositoryQueries.listFilters(filter);
        int numberUsers = 3;
        Assertions.assertNotNull(domains);
        Assertions.assertEquals(numberUsers, domains.size());
    }

    @Test
    void findPageSpecByNamesLike() {
        saveListDomainsPersonalTypeJ();
        saveListDomainsPersonalTypeF();
        List<String> names = new ArrayList<>();
        names.add("Celulose");
        DomainFilterDTO filter = DomainFilterDTO.builder()
                .names(names)
                .identificationNumbers(null)
                .personalType(null)
                .enabled(null)
                .build();
        Page<Domain> domains = domainRepositoryQueries.listFilters(filter, createSearchPageRequest(0, 10));
        int numberUsers = 3;
        Assertions.assertNotNull(domains);
        Assertions.assertEquals(numberUsers, domains.getContent().size());
    }

    @Test
    void findListSpecByNamesNotEnabled() {
        saveListDomainsPersonalTypeJ();
        saveListDomainsPersonalTypeF();
        List<String> names = new ArrayList<>();
        names.add("Nurcel Celulose Eucalipto Ltda");
        names.add("Fulano Paulista");
        names.add("Varcel Celulose Eucalipto Ltda");
        names.add("Beltrano Mineiro");
        DomainFilterDTO filter = DomainFilterDTO.builder()
                .names(names)
                .identificationNumbers(null)
                .personalType(null)
                .enabled(Boolean.FALSE)
                .build();
        List<Domain> domains = domainRepositoryQueries.listFilters(filter);
        int numberUsers = 1;
        Assertions.assertNotNull(domains);
        Assertions.assertEquals(numberUsers, domains.size());
    }

    @Test
    void findListSpecByIdentificationNumber() {
        saveListDomainsPersonalTypeJ();
        saveListDomainsPersonalTypeF();
        List<String> identificationNumbers = new ArrayList<>();
        identificationNumbers.add("45885834000160");
        identificationNumbers.add("12694046000137");
        identificationNumbers.add("29675358009");
        DomainFilterDTO filter = DomainFilterDTO.builder()
                .names(null)
                .identificationNumbers(identificationNumbers)
                .personalType(null)
                .enabled(null)
                .build();
        List<Domain> domains = domainRepositoryQueries.listFilters(filter);
        int numberUsers = 3;
        Assertions.assertNotNull(domains);
        Assertions.assertEquals(numberUsers, domains.size());
    }

    @Test
    void findPageSpecByIdentificationNumber() {
        saveListDomainsPersonalTypeJ();
        saveListDomainsPersonalTypeF();
        List<String> identificationNumbers = new ArrayList<>();
        identificationNumbers.add("45885834000160");
        identificationNumbers.add("12694046000137");
        identificationNumbers.add("29675358009");
        DomainFilterDTO filter = DomainFilterDTO.builder()
                .names(null)
                .identificationNumbers(identificationNumbers)
                .personalType(null)
                .enabled(null)
                .build();
        Page<Domain> domains = domainRepositoryQueries.listFilters(filter, createSearchPageRequest(0, 10));
        int numberUsers = 3;
        Assertions.assertNotNull(domains);
        Assertions.assertEquals(numberUsers, domains.getContent().size());
    }

    @Test
    void findListSpecByIdentificationNumberEnabled() {
        saveListDomainsPersonalTypeJ();
        saveListDomainsPersonalTypeF();
        List<String> identificationNumbers = new ArrayList<>();
        identificationNumbers.add("45885834000160");
        identificationNumbers.add("12694046000137");
        identificationNumbers.add("55469965015");
        DomainFilterDTO filter = DomainFilterDTO.builder()
                .names(null)
                .identificationNumbers(identificationNumbers)
                .personalType(null)
                .enabled(Boolean.TRUE)
                .build();
        List<Domain> domains = domainRepositoryQueries.listFilters(filter);
        int numberUsers = 2;
        Assertions.assertNotNull(domains);
        Assertions.assertEquals(numberUsers, domains.size());
    }

    @Test
    void findListSpecByEnabled() {
        saveListDomainsPersonalTypeJ();
        saveListDomainsPersonalTypeF();
        DomainFilterDTO filter = DomainFilterDTO.builder()
                .names(null)
                .identificationNumbers(null)
                .personalType(null)
                .enabled(Boolean.TRUE)
                .build();
        List<Domain> domains = domainRepositoryQueries.listFilters(filter);
        int numberUsers = 5;
        Assertions.assertNotNull(domains);
        Assertions.assertEquals(numberUsers, domains.size());
    }

    @Test
    void findListSpecByNotEnabled() {
        saveListDomainsPersonalTypeJ();
        saveListDomainsPersonalTypeF();
        DomainFilterDTO filter = DomainFilterDTO.builder()
                .names(null)
                .identificationNumbers(null)
                .personalType(null)
                .enabled(Boolean.FALSE)
                .build();
        List<Domain> domains = domainRepositoryQueries.listFilters(filter);
        int numberUsers = 1;
        Assertions.assertNotNull(domains);
        Assertions.assertEquals(numberUsers, domains.size());
    }

    @Test
    void findListSpecByPersonalTypeJ() {
        saveListDomainsPersonalTypeJ();
        saveListDomainsPersonalTypeF();
        DomainFilterDTO filter = DomainFilterDTO.builder()
                .names(null)
                .identificationNumbers(null)
                .personalType(PersonalType.J)
                .enabled(null)
                .build();
        List<Domain> domains = domainRepositoryQueries.listFilters(filter);
        int numberUsers = 3;
        Assertions.assertNotNull(domains);
        Assertions.assertEquals(numberUsers, domains.size());
    }

    @Test
    void findPageSpecByPersonalTypeJ() {
        saveListDomainsPersonalTypeJ();
        saveListDomainsPersonalTypeF();
        DomainFilterDTO filter = DomainFilterDTO.builder()
                .names(null)
                .identificationNumbers(null)
                .personalType(PersonalType.J)
                .enabled(null)
                .build();
        Page<Domain> domains = domainRepositoryQueries.listFilters(filter, createSearchPageRequest(0, 10));
        int numberUsers = 3;
        Assertions.assertNotNull(domains);
        Assertions.assertEquals(numberUsers, domains.getContent().size());
    }

    @Test
    void findListSpecByPersonalTypeF() {
        saveListDomainsPersonalTypeJ();
        saveListDomainsPersonalTypeF();
        DomainFilterDTO filter = DomainFilterDTO.builder()
                .names(null)
                .identificationNumbers(null)
                .personalType(PersonalType.F)
                .enabled(null)
                .build();
        List<Domain> domains = domainRepositoryQueries.listFilters(filter);
        int numberUsers = 3;
        Assertions.assertNotNull(domains);
        Assertions.assertEquals(numberUsers, domains.size());
    }

    @Test
    void findPageSpecByPersonalTypeF() {
        saveListDomainsPersonalTypeJ();
        saveListDomainsPersonalTypeF();
        DomainFilterDTO filter = DomainFilterDTO.builder()
                .names(null)
                .identificationNumbers(null)
                .personalType(PersonalType.F)
                .enabled(null)
                .build();
        Page<Domain> domains = domainRepositoryQueries.listFilters(filter, createSearchPageRequest(0, 10));
        int numberUsers = 3;
        Assertions.assertNotNull(domains);
        Assertions.assertEquals(numberUsers, domains.getContent().size());
    }

    @Test
    void findListSpecByPersonalTypeFandNotEnable() {
        saveListDomainsPersonalTypeJ();
        saveListDomainsPersonalTypeF();
        DomainFilterDTO filter = DomainFilterDTO.builder()
                .names(null)
                .identificationNumbers(null)
                .personalType(PersonalType.F)
                .enabled(Boolean.FALSE)
                .build();
        List<Domain> domains = domainRepositoryQueries.listFilters(filter);
        int numberUsers = 1;
        Assertions.assertNotNull(domains);
        Assertions.assertEquals(numberUsers, domains.size());
    }

    @Test
    void findPageSpecByPersonalTypeFandNotEnable() {
        saveListDomainsPersonalTypeJ();
        saveListDomainsPersonalTypeF();
        DomainFilterDTO filter = DomainFilterDTO.builder()
                .names(null)
                .identificationNumbers(null)
                .personalType(PersonalType.F)
                .enabled(Boolean.FALSE)
                .build();
        Page<Domain> domains = domainRepositoryQueries.listFilters(filter, createSearchPageRequest(0, 10));
        int numberUsers = 1;
        Assertions.assertNotNull(domains);
        Assertions.assertEquals(numberUsers, domains.getContent().size());
    }

    @Test
    void TestOneJpql() {
        UUID id = saveDomain(newDomain(null, "guacel@gmail.com", AuthenticationProvider.DATABASE, Boolean.TRUE, "14998889977",
                "1432333344", NAME, "GUACEL", "45885834000160", PersonalType.J));
        //saveListDomainsPersonalTypeJ();
        //saveListDomainsPersonalTypeF();
        Domain domain = this.domainRepositoryQueries.selectJpqlByUuid(id);
        Assertions.assertNotNull(domain);
    }

    private void saveListDomainsPersonalTypeJ() {
        saveDomain(newDomain(null, "guacel@gmail.com", AuthenticationProvider.DATABASE, Boolean.TRUE, "14998889977",
                "1432333344", NAME, "GUACEL", "45885834000160", PersonalType.J));
        saveDomain(newDomain(LocalDateTime.now(), "nurcel@gmail.com", AuthenticationProvider.DATABASE, Boolean.TRUE, "14998889978",
                "1432333345", "Nurcel Celulose Eucalipto Ltda", "NURCEL", "12694046000137", PersonalType.J));
        saveDomain(newDomain(null, "varcel@gmail.com", AuthenticationProvider.DATABASE, Boolean.TRUE, "14998889976",
                "1432333344", "Varcel Celulose Eucalipto Ltda", "VARCEL", "32821882000142", PersonalType.J));
    }

    private void saveListDomainsPersonalTypeF() {
        saveDomain(newDomain(null, "fulano@gmail.com", AuthenticationProvider.DATABASE, Boolean.TRUE, "14998889977",
                "1432333344", "Fulano Paulista", "FULANO", "29675358009", PersonalType.F));
        saveDomain(newDomain(LocalDateTime.now(), "ciclano@gmail.com", AuthenticationProvider.DATABASE, Boolean.TRUE, "14998889978",
                "1432333345", "Ciclano Carioca", "CICLANO", "14590205025", PersonalType.F));
        saveDomain(newDomain(null, "beltrano@gmail.com", AuthenticationProvider.DATABASE, Boolean.FALSE, "14998889976",
                "1432333344", "Beltrano Mineiro", "BELTRANO", "55469965015", PersonalType.F));
    }

    private Domain newDomain(LocalDateTime deleted, String email, AuthenticationProvider provider, Boolean enabled, String cellPhoneNumber,
                             String phoneNumber, String name, String identificationName, String identificationNumber, PersonalType type) {
        return Domain.builder()
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .deleted(deleted)
                .email(email)
                .authenticationProvider(provider)
                .enabled(enabled)
                .cellPhoneNumber(cellPhoneNumber)
                .phoneNumber(phoneNumber)
                .name(name)
                .identificationName(identificationName)
                .identificationNumber(identificationNumber)
                .personalType(type)
                .build();
    }

    private UUID saveDomain(Domain domain) {
        domainRepositoryQueries.getRepository().save(domain);
        return domain.getId();
    }

    private Pageable createSearchPageRequest(Integer page, Integer size) {
        if (page == null || size == null) {
            return Pageable.unpaged();
        }
        return PageRequest.of(page, size);
    }

}
