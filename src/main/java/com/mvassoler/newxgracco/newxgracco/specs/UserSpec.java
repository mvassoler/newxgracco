package com.mvassoler.newxgracco.newxgracco.specs;

import com.mvassoler.newxgracco.newxgracco.domain.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class UserSpec {

    private UserSpec() {
    }

    public static Specification<User> filterUsername(List<String> usernames) {
        if (usernames.size() > 1) {
            return UserSpec.filterUsernameList(usernames);
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("username"), "%" + usernames.get(0) + "%");
    }

    public static Specification<User> filterFirstName(List<String> firstNames) {
        if (firstNames.size() > 1) {
            return UserSpec.filterFirstNameList(firstNames);
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("firstName"), "%" + firstNames.get(0) + "%");
    }

    public static Specification<User> filterLastName(List<String> lastNames) {
        if (lastNames.size() > 1) {
            return UserSpec.filterLastNameList(lastNames);
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("lastName"), "%" + lastNames.get(0) + "%");
    }

    public static Specification<User> filterEmail(List<String> emails) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("email"), emails.get(0));
    }

    public static Specification<User> filterUsernameList(List<String> usernames) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("username")).value(usernames);
    }

    public static Specification<User> filterFirstNameList(List<String> firstNames) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("firstName")).value(firstNames);
    }

    public static Specification<User> filterLastNameList(List<String> lastNames) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("lastName")).value(lastNames);
    }
}
