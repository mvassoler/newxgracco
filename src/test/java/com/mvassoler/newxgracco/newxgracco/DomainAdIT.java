package com.mvassoler.newxgracco.newxgracco;

import com.mvassoler.newxgracco.newxgracco.domain.Domain;
import com.mvassoler.newxgracco.newxgracco.domain.DomainAd;
import com.mvassoler.newxgracco.newxgracco.dtos.DomainAdDto;
import com.mvassoler.newxgracco.newxgracco.enums.AuthenticationProvider;
import com.mvassoler.newxgracco.newxgracco.enums.PersonalType;
import com.mvassoler.newxgracco.newxgracco.filters.DomainAdFilterDTO;
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
class DomainAdIT {

    @Autowired
    private DomainAdRepositoryQueriesImpl domainAdRepositoryQueries;
    @Autowired
    private DomainRepositoryQueriesImpl domainRepositoryQueries;
    @Autowired
    private UserRepositoryQueriesImpl userRepositoryQueries;

    @BeforeEach
    public void setUp() {
        domainAdRepositoryQueries.getRepository().deleteAll();
        domainRepositoryQueries.getRepository().deleteAll();
        userRepositoryQueries.getRepository().deleteAll();
    }

    @Test
    void insertDomainAd() {
        this.saveListDomains();
        UUID id = saveOneDomainAd("45885834000160", "http://localhost:389", "base_dn_ldap_teste1", "teste1@gmail.com", "teste1@Ad123");
        Assertions.assertNotNull(id);
    }

    @Test
    void findByLdapUserNameAndLdapPasswordAndDomainDeletedIsNull() {
        this.saveListDomains();
        saveOneDomainAd("45885834000160", "http://localhost:389", "base_dn_ldap_teste1", "teste1@gmail.com", "teste1@Ad123");
        Optional<DomainAd> domainAdSaved = domainAdRepositoryQueries.getRepository().findByLdapUserNameAndLdapPasswordAndDomainDeletedIsNull("teste1@gmail.com", "teste1@Ad123");
        Assertions.assertNotNull(domainAdSaved);
        Assertions.assertEquals("teste1@gmail.com", domainAdSaved.orElseThrow().getLdapUserName());
    }

    @Test
    void findByDomainIdAndDomainDeletedIsNull() {
        this.saveListDomains();
        saveOneDomainAd("45885834000160", "http://localhost:389", "base_dn_ldap_teste1", "teste1@gmail.com", "teste1@Ad123");
        Domain domain = this.domainRepositoryQueries.getRepository().findByIdentificationNumber("45885834000160").orElseThrow();
        Optional<DomainAd> domainAdSaved = domainAdRepositoryQueries.getRepository().findByDomainIdAndDomainDeletedIsNull(domain.getId());
        Assertions.assertNotNull(domainAdSaved);
        Assertions.assertEquals("teste1@gmail.com", domainAdSaved.orElseThrow().getLdapUserName());
    }

    @Test
    void findByDomainIdAndDomainDeletedIsNullNotFound() {
        this.saveListDomains();
        saveOneDomainAd("45885834000160", "http://localhost:389", "base_dn_ldap_teste1", "teste1@gmail.com", "teste1@Ad123");
        Optional<DomainAd> domainAdSaved = domainAdRepositoryQueries.getRepository().findByDomainIdAndDomainDeletedIsNull(UUID.randomUUID());
        Assertions.assertEquals(Boolean.FALSE, domainAdSaved.isPresent());
    }

    @Test
    void findByLdapBaseDnAndDomainDeletedIsNull() {
        this.saveListDomains();
        saveOneDomainAd("45885834000160", "http://localhost:389", "base_dn_ldap_teste1", "teste1@gmail.com", "teste1@Ad123");
        Optional<DomainAd> domainAdSaved = domainAdRepositoryQueries.getRepository().findByLdapBaseDnAndDomainDeletedIsNull("base_dn_ldap_teste1");
        Assertions.assertNotNull(domainAdSaved);
        Assertions.assertEquals("base_dn_ldap_teste1", domainAdSaved.orElseThrow().getLdapBaseDn());
    }

    @Test
    void findListAllByDomainDeletedIsNull() {
        saveDomainAds();
        List<DomainAd> domainAds = this.domainAdRepositoryQueries.getRepository().findAllByDomainDeletedIsNull();
        int numberSize = 3;
        Assertions.assertNotNull(domainAds);
        Assertions.assertEquals(numberSize, domainAds.size());
    }

    @Test
    void findPageAllByDomainDeletedIsNull() {
        saveDomainAds();
        Page<DomainAd> domainAds = this.domainAdRepositoryQueries.getRepository().findAllGeneral(createSearchPageRequest(0, 10));
        int numberSize = 3;
        Assertions.assertNotNull(domainAds);
        Assertions.assertEquals(numberSize, domainAds.getContent().size());
    }

    @Test
    void findListSpecByDomainById() {
        saveDomainAds();
        List<UUID> domainIds = this.returnListDomainsIds();
        DomainAdFilterDTO filter = DomainAdFilterDTO.builder()
                .ldapBaseDns(null)
                .ldapUserNames(null)
                .ldapUrls(null)
                .domainIdentificationNames(null)
                .domainIdentificationNumbers(null)
                .domainIds(domainIds)
                .build();
        List<DomainAd> domains = domainAdRepositoryQueries.listFilters(filter);
        int numberUsers = 4;
        Assertions.assertNotNull(domains);
        Assertions.assertEquals(numberUsers, domains.size());
    }

    @Test
    void findPageSpecByDomainById() {
        saveDomainAds();
        List<UUID> domainIds = this.returnListDomainsIds();
        DomainAdFilterDTO filter = DomainAdFilterDTO.builder()
                .ldapBaseDns(null)
                .ldapUserNames(null)
                .ldapUrls(null)
                .domainIdentificationNames(null)
                .domainIdentificationNumbers(null)
                .domainIds(domainIds)
                .build();
        Page<DomainAd> domains = domainAdRepositoryQueries.listFilters(filter, createSearchPageRequest(0, 10));
        int numberUsers = 4;
        Assertions.assertNotNull(domains);
        Assertions.assertEquals(numberUsers, domains.getContent().size());
    }

    @Test
    void findListSpecByDomainByIdAndIdentificationName() {
        saveDomainAds();
        List<UUID> domainIds = this.returnListDomainsIds();
        List<String> identificationNames = new ArrayList<>();
        identificationNames.add("NURCEL");
        identificationNames.add("VARCEL");
        DomainAdFilterDTO filter = DomainAdFilterDTO.builder()
                .ldapBaseDns(null)
                .ldapUserNames(null)
                .ldapUrls(null)
                .domainIdentificationNames(identificationNames)
                .domainIdentificationNumbers(null)
                .domainIds(domainIds)
                .build();
        List<DomainAd> domains = domainAdRepositoryQueries.listFilters(filter);
        int numberUsers = 2;
        Assertions.assertNotNull(domains);
        Assertions.assertEquals(numberUsers, domains.size());
    }

    @Test
    void findPageSpecByDomainByIdAndIdentificationName() {
        saveDomainAds();
        List<UUID> domainIds = this.returnListDomainsIds();
        List<String> identificationNames = new ArrayList<>();
        identificationNames.add("NURCEL");
        identificationNames.add("VARCEL");
        DomainAdFilterDTO filter = DomainAdFilterDTO.builder()
                .ldapBaseDns(null)
                .ldapUserNames(null)
                .ldapUrls(null)
                .domainIdentificationNames(identificationNames)
                .domainIdentificationNumbers(null)
                .domainIds(domainIds)
                .build();
        Page<DomainAd> domains = domainAdRepositoryQueries.listFilters(filter, createSearchPageRequest(0, 10));
        int numberUsers = 2;
        Assertions.assertNotNull(domains);
        Assertions.assertEquals(numberUsers, domains.getContent().size());
    }

    @Test
    void findListSpecByDomainByIdAndIdentificationNumber() {
        saveDomainAds();
        List<UUID> domainIds = this.returnListDomainsIds();
        List<String> identificationNumbers = new ArrayList<>();
        identificationNumbers.add("45885834000160");
        identificationNumbers.add("53871537000193");
        DomainAdFilterDTO filter = DomainAdFilterDTO.builder()
                .ldapBaseDns(null)
                .ldapUserNames(null)
                .ldapUrls(null)
                .domainIdentificationNames(null)
                .domainIdentificationNumbers(identificationNumbers)
                .domainIds(domainIds)
                .build();
        List<DomainAd> domains = domainAdRepositoryQueries.listFilters(filter);
        int numberUsers = 2;
        String identificationNameOne = "GUACEL";
        String identificationNameTwo = "DARACEL";
        Assertions.assertNotNull(domains);
        Assertions.assertEquals(numberUsers, domains.size());
        Assertions.assertEquals(identificationNameOne, domains.stream().filter(domainAd -> domainAd.getDomain().getIdentificationName().equals(identificationNameOne)).findFirst().orElseThrow().getDomain().getIdentificationName());
        Assertions.assertEquals(identificationNameTwo, domains.stream().filter(domainAd -> domainAd.getDomain().getIdentificationName().equals(identificationNameTwo)).findFirst().orElseThrow().getDomain().getIdentificationName());
    }

    @Test
    void findPageSpecByDomainByIdAndIdentificationNumber() {
        saveDomainAds();
        List<UUID> domainIds = this.returnListDomainsIds();
        List<String> identificationNumbers = new ArrayList<>();
        identificationNumbers.add("45885834000160");
        identificationNumbers.add("53871537000193");
        DomainAdFilterDTO filter = DomainAdFilterDTO.builder()
                .ldapBaseDns(null)
                .ldapUserNames(null)
                .ldapUrls(null)
                .domainIdentificationNames(null)
                .domainIdentificationNumbers(identificationNumbers)
                .domainIds(domainIds)
                .build();
        Page<DomainAd> domains = domainAdRepositoryQueries.listFilters(filter, createSearchPageRequest(0, 10));
        int numberUsers = 2;
        String identificationNameOne = "GUACEL";
        String identificationNameTwo = "DARACEL";
        Assertions.assertNotNull(domains);
        Assertions.assertEquals(numberUsers, domains.getContent().size());
        Assertions.assertEquals(identificationNameOne, domains.stream().filter(domainAd -> domainAd.getDomain().getIdentificationName().equals(identificationNameOne)).findFirst().orElseThrow().getDomain().getIdentificationName());
        Assertions.assertEquals(identificationNameTwo, domains.stream().filter(domainAd -> domainAd.getDomain().getIdentificationName().equals(identificationNameTwo)).findFirst().orElseThrow().getDomain().getIdentificationName());
    }

    @Test
    void findListSpecByBaseDn() {
        saveDomainAds();
        List<String> ldapBaseDns = new ArrayList<>();
        ldapBaseDns.add("base_dn_ldap_teste2");
        ldapBaseDns.add("base_dn_ldap_teste3");
        DomainAdFilterDTO filter = DomainAdFilterDTO.builder()
                .ldapBaseDns(ldapBaseDns)
                .ldapUserNames(null)
                .ldapUrls(null)
                .domainIdentificationNames(null)
                .domainIdentificationNumbers(null)
                .domainIds(null)
                .build();
        List<DomainAd> domains = domainAdRepositoryQueries.listFilters(filter);
        int numberUsers = 2;
        Assertions.assertNotNull(domains);
        Assertions.assertEquals(numberUsers, domains.size());
    }

    @Test
    void findPageSpecByBaseDn() {
        saveDomainAds();
        List<String> ldapBaseDns = new ArrayList<>();
        ldapBaseDns.add("base_dn_ldap_teste2");
        ldapBaseDns.add("base_dn_ldap_teste3");
        DomainAdFilterDTO filter = DomainAdFilterDTO.builder()
                .ldapBaseDns(ldapBaseDns)
                .ldapUserNames(null)
                .ldapUrls(null)
                .domainIdentificationNames(null)
                .domainIdentificationNumbers(null)
                .domainIds(null)
                .build();
        Page<DomainAd> domains = domainAdRepositoryQueries.listFilters(filter, createSearchPageRequest(0, 10));
        int numberUsers = 2;
        Assertions.assertNotNull(domains);
        Assertions.assertEquals(numberUsers, domains.getContent().size());
    }

    @Test
    void findListSpecUrls() {
        saveDomainAds();
        List<String> ldapUrls = new ArrayList<>();
        ldapUrls.add("http://localhost:389");
        ldapUrls.add("http://localhost:391");
        ldapUrls.add("http://localhost:392");
        DomainAdFilterDTO filter = DomainAdFilterDTO.builder()
                .ldapBaseDns(null)
                .ldapUserNames(null)
                .ldapUrls(ldapUrls)
                .domainIdentificationNames(null)
                .domainIdentificationNumbers(null)
                .domainIds(null)
                .build();
        List<DomainAd> domains = domainAdRepositoryQueries.listFilters(filter);
        int numberUsers = 3;
        Assertions.assertNotNull(domains);
        Assertions.assertEquals(numberUsers, domains.size());
    }

    @Test
    void findPageSpecUrls() {
        saveDomainAds();
        List<String> ldapUrls = new ArrayList<>();
        ldapUrls.add("http://localhost:389");
        ldapUrls.add("http://localhost:391");
        ldapUrls.add("http://localhost:392");
        DomainAdFilterDTO filter = DomainAdFilterDTO.builder()
                .ldapBaseDns(null)
                .ldapUserNames(null)
                .ldapUrls(ldapUrls)
                .domainIdentificationNames(null)
                .domainIdentificationNumbers(null)
                .domainIds(null)
                .build();
        Page<DomainAd> domains = domainAdRepositoryQueries.listFilters(filter, createSearchPageRequest(0, 10));
        int numberUsers = 3;
        Assertions.assertNotNull(domains);
        Assertions.assertEquals(numberUsers, domains.getContent().size());
    }

    @Test
    void findListSpecUsernames() {
        saveDomainAds();
        List<String> ldapUserNames = new ArrayList<>();
        ldapUserNames.add("teste1@gmail.com");
        ldapUserNames.add("teste3@gmail.com");
        DomainAdFilterDTO filter = DomainAdFilterDTO.builder()
                .ldapBaseDns(null)
                .ldapUserNames(ldapUserNames)
                .ldapUrls(null)
                .domainIdentificationNames(null)
                .domainIdentificationNumbers(null)
                .domainIds(null)
                .build();
        List<DomainAd> domains = domainAdRepositoryQueries.listFilters(filter);
        int numberUsers = 2;
        Assertions.assertNotNull(domains);
        Assertions.assertEquals(numberUsers, domains.size());
    }

    @Test
    void findPageSpecUsernames() {
        saveDomainAds();
        List<String> ldapUserNames = new ArrayList<>();
        ldapUserNames.add("teste1@gmail.com");
        ldapUserNames.add("teste3@gmail.com");
        DomainAdFilterDTO filter = DomainAdFilterDTO.builder()
                .ldapBaseDns(null)
                .ldapUserNames(ldapUserNames)
                .ldapUrls(null)
                .domainIdentificationNames(null)
                .domainIdentificationNumbers(null)
                .domainIds(null)
                .build();
        Page<DomainAd> domains = domainAdRepositoryQueries.listFilters(filter, createSearchPageRequest(0, 10));
        int numberUsers = 2;
        Assertions.assertNotNull(domains);
        Assertions.assertEquals(numberUsers, domains.getContent().size());
    }

    @Test
    void findDomainForJpql() {
        this.saveListDomains();
        UUID id = saveOneDomainAd("45885834000160", "http://localhost:389", "base_dn_ldap_teste1", "teste1@gmail.com", "teste1@Ad123");
        Domain domain = this.domainAdRepositoryQueries.selectJpqlDomainById(id);
        Assertions.assertNotNull(domain);
    }

    @Test
    void findUrlAndBaseDnForJpql() {
        this.saveListDomains();
        UUID id = saveOneDomainAd("45885834000160", "http://localhost:389", "base_dn_ldap_teste1", "teste1@gmail.com", "teste1@Ad123");
        Object[] retorno = this.domainAdRepositoryQueries.selectJpqlProjectionUrlAndBaseDnById(id);
        Assertions.assertNotNull(retorno);
    }

    @Test
    void findListUrlAndBaseDnForJpql() {
        saveDomainAds();
        List<Object[]> retorno = this.domainAdRepositoryQueries.selectJpqlProjectionListUrlAndBaseDnById();
        int size = 4;
        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(size, retorno.size());
    }

    @Test
    void findLisInDtoUrlAndBaseDnForJpql() {
        saveDomainAds();
        List<DomainAdDto> retorno = this.domainAdRepositoryQueries.selectJpqlProjectionDtoListUrlAndBaseDnById();
        int size = 4;
        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(size, retorno.size());
    }

    @Test
    void findListJpqlByIdentificationName() {
        saveDomainAds();
        List<DomainAd> domainAds = this.domainAdRepositoryQueries.selectJpqlJoinByIdentificationName("nurcel");
        int size = 1;
        Assertions.assertNotNull(domainAds);
        Assertions.assertEquals(size, domainAds.size());
    }

    @Test
    void findListJpqlLeftJoinByIdentificationName() {
        saveDomainAds();
        List<DomainAd> domainAds = this.domainAdRepositoryQueries.selectJpqlLeftJoinByIdentificationName("NURCEL");
        int size = 4;
        Assertions.assertNotNull(domainAds);
        Assertions.assertEquals(size, domainAds.size());
    }

    @Test
    void findListJpqlLikeJoinByName() {
        saveDomainAds();
        List<DomainAd> domainAds = this.domainAdRepositoryQueries.selectJpqlJoinLikeByName("Celulose");
        int size = 3;
        Assertions.assertNotNull(domainAds);
        Assertions.assertEquals(size, domainAds.size());
    }


    private UUID saveOneDomainAd(String identificationNumber, String ldapUrl, String ldapBaseDn, String username, String password) {
        Domain domain = this.domainRepositoryQueries.getRepository().findByIdentificationNumber(identificationNumber).orElseThrow();
        DomainAd domainAdSaved = this.newDomainAd(domain, ldapUrl, ldapBaseDn, username, password);
        this.domainAdRepositoryQueries.getRepository().save(domainAdSaved);
        return domainAdSaved.getId();
    }

    private void saveDomainAds() {
        this.saveListDomains();
        this.saveOneDomainAd("45885834000160", "http://localhost:389", "base_dn_ldap_teste1", "teste1@gmail.com", "teste1@Ad123");
        this.saveOneDomainAd("12694046000137", "http://localhost:390", "base_dn_ldap_teste2", "teste2@gmail.com", "teste2@Ad123");
        this.saveOneDomainAd("32821882000142", "http://localhost:391", "base_dn_ldap_teste3", "teste3@gmail.com", "teste3@Ad123");
        this.saveOneDomainAd("53871537000193", "http://localhost:392", "base_dn_ldap_teste4", "teste4@gmail.com", "teste4@Ad123");
    }

    private DomainAd newDomainAd(Domain domain, String ldapUrl, String ldapBaseDn, String username, String password) {
        return DomainAd.builder()
                .domain(domain)
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .ldapUrl(ldapUrl)
                .ldapBaseDn(ldapBaseDn)
                .ldapUserName(username)
                .ldapPassword(password)
                .build();
    }

    private void saveListDomains() {
        saveDomain(newDomain(null, "guacel@gmail.com", AuthenticationProvider.ACTIVE_DIRECTORY, Boolean.TRUE, "14998889977",
                "1432333344", "Guacel Celulose Eucalipto Ltda", "GUACEL", "45885834000160", PersonalType.J));
        saveDomain(newDomain(LocalDateTime.now(), "nurcel@gmail.com", AuthenticationProvider.ACTIVE_DIRECTORY, Boolean.TRUE, "14998889978",
                "1432333345", "Nurcel Celulose Eucalipto Ltda", "NURCEL", "12694046000137", PersonalType.J));
        saveDomain(newDomain(null, "varcel@gmail.com", AuthenticationProvider.ACTIVE_DIRECTORY, Boolean.TRUE, "14998889976",
                "1432333344", "Varcel Celulose Eucalipto Ltda", "VARCEL", "32821882000142", PersonalType.J));
        saveDomain(newDomain(null, "Darace@gmail.com", AuthenticationProvider.ACTIVE_DIRECTORY, Boolean.FALSE, "14998889974",
                "1432333322", "Daracel Madeireira Ltda", "DARACEL", "53871537000193", PersonalType.J));
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

    private List<UUID> returnListDomainsIds() {
        List<UUID> domainIds = new ArrayList<>();
        domainIds.add(this.domainRepositoryQueries.getRepository().findByIdentificationNumber("45885834000160").orElseThrow().getId());
        domainIds.add(this.domainRepositoryQueries.getRepository().findByIdentificationNumber("12694046000137").orElseThrow().getId());
        domainIds.add(this.domainRepositoryQueries.getRepository().findByIdentificationNumber("32821882000142").orElseThrow().getId());
        domainIds.add(this.domainRepositoryQueries.getRepository().findByIdentificationNumber("53871537000193").orElseThrow().getId());
        return domainIds;
    }

    private void saveDomain(Domain domain) {
        domainRepositoryQueries.getRepository().save(domain);
    }

    private Pageable createSearchPageRequest(Integer page, Integer size) {
        if (page == null || size == null) {
            return Pageable.unpaged();
        }
        return PageRequest.of(page, size);
    }
}
