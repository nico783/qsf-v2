package com.adeo.qsf.service;

import io.github.jhipster.service.Criteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * Operating framework of the "QueryService".
 *
 * Service for executing complex queries for entities in the database.
 * The main input is a {@link Criteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of DTO which fulfills the criteria.
 *
 * @param <C>   relative criteria
 * @param <DTO> relative DTO
 * @author Nicolas Benizri
 */
public interface EntityQueryService<C extends Criteria, DTO> {

    /**
     * Return a {@link Page} of DTO which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    Page<DTO> findByCriteria(C criteria, Pageable page);

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    long countByCriteria(C criteria);

}
