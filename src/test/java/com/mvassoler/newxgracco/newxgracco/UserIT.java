package com.mvassoler.newxgracco.newxgracco;

import com.mvassoler.newxgracco.newxgracco.domain.User;
import com.mvassoler.newxgracco.newxgracco.filters.UserFilterDTO;
import com.mvassoler.newxgracco.newxgracco.queries.DomainAdRepositoryQueriesImpl;
import com.mvassoler.newxgracco.newxgracco.queries.DomainRepositoryQueriesImpl;
import com.mvassoler.newxgracco.newxgracco.queries.UserRepositoryQueriesImpl;
import org.apache.commons.lang3.StringUtils;
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
class UserIT {


    @Autowired
    private UserRepositoryQueriesImpl userRepositoryQueries;
    @Autowired
    private DomainRepositoryQueriesImpl domainRepositoryQueries;
    @Autowired
    private DomainAdRepositoryQueriesImpl domainAdRepositoryQueries;


    @BeforeEach
    public void setUp() {
        domainAdRepositoryQueries.getRepository().deleteAll();
        domainRepositoryQueries.getRepository().deleteAll();
        userRepositoryQueries.getRepository().deleteAll();
    }

    @Test
    void insertUser() {
        User user = newUser();
        userRepositoryQueries.getRepository().save(user);
        Assertions.assertNotNull(user.getId());
    }

    @Test
    void findByUsername() {
        UUID id = saveUserAndReturnUUID(newUser());
        userRepositoryQueries.getRepository().findByUsernameAndEnabledIsTrue("fulanodetal");
        Assertions.assertNotNull(id);
    }

    @Test
    void findByUsernameEnableAndNotEnable() {
        saveUserAndReturnUUID(newUser());
        saveUserAndReturnUUID(newUserNotEnable());
        Optional<User> userNotFind = userRepositoryQueries.getRepository().findByUsernameAndEnabledIsTrue("beltranodetal");
        Optional<User> userFind = userRepositoryQueries.getRepository().findByUsername("beltranodetal");
        Assertions.assertEquals(Boolean.FALSE, userNotFind.isPresent());
        Assertions.assertEquals(Boolean.TRUE, userFind.isPresent());
    }

    @Test
    void findBYid() {
        UUID id = saveUserAndReturnUUID(newUser());
        Optional<User> userSaved = userRepositoryQueries.getRepository().findById(id);
        Assertions.assertNotNull(userSaved.orElseThrow());
    }

    @Test
    void findByCustom() {
        saveUserAndReturnUUID(newUser());
        String username = "fulanodetal";
        Page<User> pagedUsers = userRepositoryQueries.getRepository().findByCustom(
                null, null, StringUtils.lowerCase(username), null, createSearchPageRequest(0, 10));
        int numberUsers = 1;
        Assertions.assertEquals(numberUsers, pagedUsers.getContent().size());
    }

    @Test
    void findAll() {
        saveUserAndReturnUUID(newUser());
        List<User> users = userRepositoryQueries.getRepository().findAll();
        int numberUsers = 1;
        Assertions.assertNotNull(users);
        Assertions.assertEquals(numberUsers, users.size());
    }

    @Test
    void findAllBySpec() {
        saveUserAndReturnUUID(newUser());
        saveUserAndReturnUUID(newUserDeleted());
        saveUserAndReturnUUID(newUserNotEnable());

        List<String> lastNames = new ArrayList<>();
        lastNames.add("de Tal");
        UserFilterDTO filter = UserFilterDTO.builder()
                .usernames(null)
                .firstNames(null)
                .lastNames(lastNames)
                .emails(null)
                .build();
        List<User> users = userRepositoryQueries.listFilters(filter);
        int numberUsers = 3;
        Assertions.assertNotNull(users);
        Assertions.assertEquals(numberUsers, users.size());
    }

    @Test
    void findAllBySpecPage() {
        saveUserAndReturnUUID(newUser());
        saveUserAndReturnUUID(newUserFulanoDuplicate());
        saveUserAndReturnUUID(newUserDeleted());
        saveUserAndReturnUUID(newUserNotEnable());

        List<String> usernames = new ArrayList<>();
        usernames.add("fulanodetal");
        usernames.add("ciclanodetal");
        usernames.add("beltranodetal");
        UserFilterDTO filter = UserFilterDTO.builder()
                .usernames(usernames)
                .firstNames(null)
                .lastNames(null)
                .emails(null)
                .build();
        Page<User> users = userRepositoryQueries.listFilters(filter, createSearchPageRequest(0, 10));
        int numberUsers = 3;
        Assertions.assertNotNull(users);
        Assertions.assertEquals(numberUsers, users.getContent().size());
    }


    @Test
    void findAllNotDeleted() {
        saveUserAndReturnUUID(newUser());
        saveUserAndReturnUUID(newUserDeleted());
        Page<User> users = userRepositoryQueries.getRepository().findAllGeneral(createSearchPageRequest(0, 10));
        int numberUsers = 1;
        Assertions.assertNotNull(users);
        Assertions.assertEquals(numberUsers, users.getContent().size());
    }

    private UUID saveUserAndReturnUUID(User user) {
        userRepositoryQueries.getRepository().save(user);
        return user.getId();
    }

    private User newUser() {
        return User.builder()
                .superUser(Boolean.TRUE)
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .customer(Boolean.FALSE)
                .email("fulano@gmail.com")
                .enabled(Boolean.TRUE)
                .firstName("Fulano")
                .lastName("de Tal")
                .username("fulanodetal")
                .resetPasswordRequired(Boolean.FALSE)
                .password("gmail@1525")
                .build();
    }

    private User newUserFulanoDuplicate() {
        return User.builder()
                .superUser(Boolean.TRUE)
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .customer(Boolean.FALSE)
                .email("fulano2@gmail.com")
                .enabled(Boolean.TRUE)
                .firstName("Fulano")
                .lastName("de Tal")
                .username("fulanodetal2")
                .resetPasswordRequired(Boolean.FALSE)
                .password("gmail@45545")
                .build();
    }

    private User newUserDeleted() {
        return User.builder()
                .superUser(Boolean.TRUE)
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .deleted(LocalDateTime.now())
                .customer(Boolean.FALSE)
                .email("ciclano@gmail.com")
                .enabled(Boolean.TRUE)
                .firstName("Ciclano")
                .lastName("de Tal")
                .username("ciclanodetal")
                .resetPasswordRequired(Boolean.FALSE)
                .password("gmail@1526")
                .build();
    }

    private User newUserNotEnable() {
        return User.builder()
                .superUser(Boolean.TRUE)
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .customer(Boolean.FALSE)
                .email("Beltrano@gmail.com")
                .enabled(Boolean.FALSE)
                .firstName("Beltrano")
                .lastName("de Tal")
                .username("beltranodetal")
                .resetPasswordRequired(Boolean.FALSE)
                .password("gmail@1527")
                .build();
    }

    private Pageable createSearchPageRequest(Integer page, Integer size) {
        if (page == null || size == null) {
            return Pageable.unpaged();
        }
        return PageRequest.of(page, size);
    }
}
