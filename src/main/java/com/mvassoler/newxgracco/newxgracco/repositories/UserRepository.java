package com.mvassoler.newxgracco.newxgracco.repositories;

import com.mvassoler.newxgracco.newxgracco.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CustomJpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    @Query(" from User u where u.deleted is null")
    Page<User> findAllGeneral(Pageable pageable);

    Optional<User> findByUsernameAndEnabledIsTrue(String username);

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE (:firstName is null OR LOWER(u.firstName) LIKE %:firstName%)" +
            " AND (:lastName is null OR LOWER(u.lastName) LIKE %:lastName%)" +
            " AND (:username is null OR LOWER(u.username) LIKE %:username%)" +
            " AND (:enabled is null OR u.enabled = :enabled)" +
            " AND (u.deleted is null)")
    Page<User> findByCustom(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("username") String username, @Param("enabled") Boolean enabled, Pageable pageable);

}
