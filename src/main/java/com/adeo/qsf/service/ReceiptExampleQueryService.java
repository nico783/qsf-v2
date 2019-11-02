package com.adeo.qsf.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.adeo.qsf.domain.ReceiptExample;
import com.adeo.qsf.domain.*; // for static metamodels
import com.adeo.qsf.repository.ReceiptExampleRepository;
import com.adeo.qsf.service.dto.ReceiptExampleCriteria;
import com.adeo.qsf.service.dto.ReceiptExampleDTO;
import com.adeo.qsf.service.mapper.ReceiptExampleMapper;

/**
 * Service for executing complex queries for {@link ReceiptExample} entities in the database.
 * The main input is a {@link ReceiptExampleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ReceiptExampleDTO} or a {@link Page} of {@link ReceiptExampleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReceiptExampleQueryService extends QueryService<ReceiptExample> {

    private final Logger log = LoggerFactory.getLogger(ReceiptExampleQueryService.class);

    private final ReceiptExampleRepository receiptExampleRepository;

    private final ReceiptExampleMapper receiptExampleMapper;

    public ReceiptExampleQueryService(ReceiptExampleRepository receiptExampleRepository, ReceiptExampleMapper receiptExampleMapper) {
        this.receiptExampleRepository = receiptExampleRepository;
        this.receiptExampleMapper = receiptExampleMapper;
    }

    /**
     * Return a {@link List} of {@link ReceiptExampleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ReceiptExampleDTO> findByCriteria(ReceiptExampleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ReceiptExample> specification = createSpecification(criteria);
        return receiptExampleMapper.toDto(receiptExampleRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ReceiptExampleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ReceiptExampleDTO> findByCriteria(ReceiptExampleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ReceiptExample> specification = createSpecification(criteria);
        return receiptExampleRepository.findAll(specification, page)
            .map(receiptExampleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReceiptExampleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ReceiptExample> specification = createSpecification(criteria);
        return receiptExampleRepository.count(specification);
    }

    /**
     * Function to convert ReceiptExampleCriteria to a {@link Specification}.
     */
    private Specification<ReceiptExample> createSpecification(ReceiptExampleCriteria criteria) {
        Specification<ReceiptExample> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ReceiptExample_.id));
            }
            if (criteria.getState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getState(), ReceiptExample_.state));
            }
        }
        return specification;
    }
}
