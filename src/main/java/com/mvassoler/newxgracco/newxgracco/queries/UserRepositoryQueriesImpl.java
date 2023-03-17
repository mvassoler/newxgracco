package com.mvassoler.newxgracco.newxgracco.queries;

import com.mvassoler.newxgracco.newxgracco.domain.User;
import com.mvassoler.newxgracco.newxgracco.filters.UserFilterDTO;
import com.mvassoler.newxgracco.newxgracco.repositories.UserRepository;
import com.mvassoler.newxgracco.newxgracco.specs.UserSpec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;


@Repository
public class UserRepositoryQueriesImpl implements RepositoryQueries<User, UserFilterDTO, UserRepository> {

    private final UserRepository userRepository;

    public UserRepositoryQueriesImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> listFilters(UserFilterDTO filter) {
        return userRepository.findAll(this.madeSpecifications(filter));
    }

    @Override
    public Page<User> listFilters(UserFilterDTO filter, Pageable pageable) {
        return userRepository.findAll(this.madeSpecifications(filter), pageable);
    }

    @Override
    public Specification<User> madeSpecifications(UserFilterDTO filter) {
        Specification<User> specification = null;
        specification = this.madeSpecificationUsername(specification, filter);
        specification = this.madeSpecificationFirstName(specification, filter);
        specification = this.madeSpecificationLastName(specification, filter);
        specification = this.madeSpecificationEmail(specification, filter);
        return specification;
    }

    @Override
    public UserRepository getRepository() {
        return this.userRepository;
    }

    private Specification<User> madeSpecificationUsername(Specification<User> specification, UserFilterDTO filter) {
        if (!Objects.isNull(filter.getUsernames()) && !filter.getUsernames().isEmpty()) {
            if (Objects.isNull(specification)) {
                specification = UserSpec.filterUsername(filter.getUsernames());
            } else {
                specification = specification.and(UserSpec.filterUsername(filter.getUsernames()));
            }
        }
        return specification;
    }

    private Specification<User> madeSpecificationFirstName(Specification<User> specification, UserFilterDTO filter) {
        if (!Objects.isNull(filter.getFirstNames()) && !filter.getFirstNames().isEmpty()) {
            if (Objects.isNull(specification)) {
                specification = UserSpec.filterFirstName(filter.getFirstNames());
            } else {
                specification = specification.and(UserSpec.filterFirstName(filter.getFirstNames()));
            }
        }
        return specification;
    }


    private Specification<User> madeSpecificationLastName(Specification<User> specification, UserFilterDTO filter) {
        if (!Objects.isNull(filter.getLastNames()) && !filter.getLastNames().isEmpty()) {
            if (Objects.isNull(specification)) {
                specification = UserSpec.filterLastName(filter.getLastNames());
            } else {
                specification = specification.and(UserSpec.filterLastName(filter.getLastNames()));
            }
        }
        return specification;
    }

    private Specification<User> madeSpecificationEmail(Specification<User> specification, UserFilterDTO filter) {
        if (!Objects.isNull(filter.getEmails()) && !filter.getEmails().isEmpty()) {
            if (Objects.isNull(specification)) {
                specification = UserSpec.filterEmail(filter.getEmails());
            } else {
                specification = specification.and(UserSpec.filterEmail(filter.getEmails()));
            }
        }
        return specification;
    }

}
