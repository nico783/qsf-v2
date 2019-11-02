package com.adeo.qsf.service;

import io.github.jhipster.service.QueryService;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.RangeFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.metamodel.SingularAttribute;

/**
 * Custom QueryService implementation in order to correct anomalies of the parent component.
 *
 * @author Nicolas Benizri
 */
public class CorrectedQueryService<ENTITY> extends QueryService<ENTITY> {

    /**
     * This method allows the management of RangeFilters (and therefore GreaterThan, GreaterOrEqualThan, LessThan, LessOrEqualThan, LessOrEqualThan filters).
     * Without this method, the code used will default to {@link io.github.jhipster.service.QueryService#buildSpecification(Filter, SingularAttribute)}
     * and the filters specific to the RangeFilter class will not be applied (e.g. filters on GreaterThan ids will not work).
     */
     protected <X extends Comparable<? super X>> Specification<ENTITY> buildSpecification(RangeFilter<X> filter, SingularAttribute<? super ENTITY, X> field) {
        return this.buildSpecification(filter, (root) -> root.get(field));
    }

}
