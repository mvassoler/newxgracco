package com.mvassoler.newxgracco.newxgracco.specs;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface GenericSpec<T> {

    default Specification<T> filterAtributeString(List<String> values, String atributeName) {
        if (values.size() > 1) {
            return filterAtributeStringList(values, atributeName);
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(atributeName), "%" + values.get(0) + "%");
    }

    default Specification<T> filterAtributeRelationalString(List<String> values, String nameModelRelational, String atributeName) {
        if (values.size() > 1) {
            return filterAtributeRelationalStringList(values, nameModelRelational, atributeName);
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(nameModelRelational).get(atributeName), "%" + values.get(0) + "%");
    }

    default Specification<T> filterAtributeUUIDList(List<UUID> values, String atributeName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(atributeName)).value(values);
    }

    default Specification<T> filterAtributeRelationalUUIDList(List<UUID> values, String nameModelRelational, String atributeName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(nameModelRelational).get(atributeName)).value(values);
    }

    default Specification<T> filterAtributeStringList(List<String> values, String atributeName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(atributeName)).value(values);
    }

    default Specification<T> filterAtributeRelationalStringList(List<String> values, String nameModelRelational, String atributeName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(nameModelRelational).get(atributeName)).value(values);
    }

}
