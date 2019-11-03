package com.adeo.qsf.service;


import com.adeo.qsf.domain.CommandExample;
import com.adeo.qsf.domain.CommandExample_;
import com.adeo.qsf.domain.ReceiptExample_;
import com.adeo.qsf.repository.CommandExampleRepository;
import com.adeo.qsf.service.dto.CommandReceiptCriteria;
import com.adeo.qsf.service.dto.CommandReceiptDTO;
import com.adeo.qsf.service.mapper.CommandReceiptMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;

/**
 * Service for executing complex queries for {@link CommandExample} entities in the database.
 * The main input is a {@link CommandReceiptCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link CommandReceiptDTO} which fulfills the criteria.
 *
 * @author Nicolas Benizri
 */
@Service
@Transactional(readOnly = true)
public class CommandReceiptQueryService extends CorrectedQueryService<CommandExample> implements EntityQueryService<CommandReceiptCriteria, CommandReceiptDTO> {

    private final Logger log = LoggerFactory.getLogger(CommandReceiptQueryService.class);

    private final CommandExampleRepository commandExampleRepository;
    private final CommandReceiptMapper commandReceiptMapper;

    public CommandReceiptQueryService(CommandExampleRepository commandExampleRepository, CommandReceiptMapper commandReceiptMapper) {
        this.commandExampleRepository = commandExampleRepository;
        this.commandReceiptMapper = commandReceiptMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<CommandReceiptDTO> findByCriteria(CommandReceiptCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CommandExample> specification = createSpecification(criteria);
        return commandExampleRepository.findAll(specification, page)
            .map(commandReceiptMapper::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long countByCriteria(CommandReceiptCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CommandExample> specification = createSpecification(criteria);
        return commandExampleRepository.count(specification);
    }

    /**
     * Function to convert CommandReceiptCriteria to a {@link Specification}.
     */
    private Specification<CommandExample> createSpecification(CommandReceiptCriteria criteria) {
        Specification<CommandExample> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getCommandId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommandId(), CommandExample_.id));
            }

            if (criteria.getCommandDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCommandDescription(), CommandExample_.description));
            }

            if (criteria.getReceiptId() != null) {
                specification = specification.and(buildSpecification(criteria.getReceiptId(), root -> root.join(CommandExample_.receiptExample, JoinType.LEFT).get(ReceiptExample_.id)));
            }

            if (criteria.getReceiptState() != null) {
                specification = specification.and(buildSpecification(criteria.getReceiptState(), root -> root.join(CommandExample_.receiptExample, JoinType.LEFT).get(ReceiptExample_.state)));
            }
        }
        return specification;
    }

}
