package com.mvassoler.newxgracco.newxgracco.queries;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;


public interface RepositoryQueries<E, F, R> {

    List<E> listFilters(F filter);

    Page<E> listFilters(F filter, Pageable pageable);

    Specification<E> madeSpecifications(F filter);

    R getRepository();

}
