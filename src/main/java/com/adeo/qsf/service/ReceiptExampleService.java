package com.adeo.qsf.service;

import com.adeo.qsf.domain.ReceiptExample;
import com.adeo.qsf.repository.ReceiptExampleRepository;
import com.adeo.qsf.service.dto.ReceiptExampleDTO;
import com.adeo.qsf.service.mapper.ReceiptExampleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ReceiptExample}.
 */
@Service
@Transactional
public class ReceiptExampleService {

    private final Logger log = LoggerFactory.getLogger(ReceiptExampleService.class);

    private final ReceiptExampleRepository receiptExampleRepository;

    private final ReceiptExampleMapper receiptExampleMapper;

    public ReceiptExampleService(ReceiptExampleRepository receiptExampleRepository, ReceiptExampleMapper receiptExampleMapper) {
        this.receiptExampleRepository = receiptExampleRepository;
        this.receiptExampleMapper = receiptExampleMapper;
    }

    /**
     * Save a receiptExample.
     *
     * @param receiptExampleDTO the entity to save.
     * @return the persisted entity.
     */
    public ReceiptExampleDTO save(ReceiptExampleDTO receiptExampleDTO) {
        log.debug("Request to save ReceiptExample : {}", receiptExampleDTO);
        ReceiptExample receiptExample = receiptExampleMapper.toEntity(receiptExampleDTO);
        receiptExample = receiptExampleRepository.save(receiptExample);
        return receiptExampleMapper.toDto(receiptExample);
    }

    /**
     * Get all the receiptExamples.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ReceiptExampleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReceiptExamples");
        return receiptExampleRepository.findAll(pageable)
            .map(receiptExampleMapper::toDto);
    }


    /**
     * Get one receiptExample by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ReceiptExampleDTO> findOne(Long id) {
        log.debug("Request to get ReceiptExample : {}", id);
        return receiptExampleRepository.findById(id)
            .map(receiptExampleMapper::toDto);
    }

    /**
     * Delete the receiptExample by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ReceiptExample : {}", id);
        receiptExampleRepository.deleteById(id);
    }
}
