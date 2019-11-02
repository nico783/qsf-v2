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

import com.adeo.qsf.domain.CommandExample;
import com.adeo.qsf.domain.*; // for static metamodels
import com.adeo.qsf.repository.CommandExampleRepository;
import com.adeo.qsf.service.dto.CommandExampleCriteria;
import com.adeo.qsf.service.dto.CommandExampleDTO;
import com.adeo.qsf.service.mapper.CommandExampleMapper;

/**
 * Service for executing complex queries for {@link CommandExample} entities in the database.
 * The main input is a {@link CommandExampleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CommandExampleDTO} or a {@link Page} of {@link CommandExampleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommandExampleQueryService extends CorrectedQueryService<CommandExample> {

    private final Logger log = LoggerFactory.getLogger(CommandExampleQueryService.class);

    private final CommandExampleRepository commandExampleRepository;

    private final CommandExampleMapper commandExampleMapper;

    public CommandExampleQueryService(CommandExampleRepository commandExampleRepository, CommandExampleMapper commandExampleMapper) {
        this.commandExampleRepository = commandExampleRepository;
        this.commandExampleMapper = commandExampleMapper;
    }

    /**
     * Return a {@link Page} of {@link CommandExampleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommandExampleDTO> findByCriteria(CommandExampleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CommandExample> specification = createSpecification(criteria);
        return commandExampleRepository.findAll(specification, page)
            .map(commandExampleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommandExampleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CommandExample> specification = createSpecification(criteria);
        return commandExampleRepository.count(specification);
    }

    /**
     * Function to convert CommandExampleCriteria to a {@link Specification}.
     */
    private Specification<CommandExample> createSpecification(CommandExampleCriteria criteria) {
        Specification<CommandExample> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CommandExample_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CommandExample_.description));
            }
            if (criteria.getReceiptExampleId() != null) {
                specification = specification.and(buildSpecification(criteria.getReceiptExampleId(), root -> root.join(CommandExample_.receiptExample, JoinType.LEFT).get(ReceiptExample_.id)));
            }
        }
        return specification;
    }
}
