package com.adeo.qsf.web.rest;

import com.adeo.qsf.service.ReceiptExampleService;
import com.adeo.qsf.web.rest.errors.BadRequestAlertException;
import com.adeo.qsf.service.dto.ReceiptExampleDTO;
import com.adeo.qsf.service.dto.ReceiptExampleCriteria;
import com.adeo.qsf.service.ReceiptExampleQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.adeo.qsf.domain.ReceiptExample}.
 */
@RestController
@RequestMapping("/api")
public class ReceiptExampleResource {

    private final Logger log = LoggerFactory.getLogger(ReceiptExampleResource.class);

    private static final String ENTITY_NAME = "receiptExample";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReceiptExampleService receiptExampleService;

    private final ReceiptExampleQueryService receiptExampleQueryService;

    public ReceiptExampleResource(ReceiptExampleService receiptExampleService, ReceiptExampleQueryService receiptExampleQueryService) {
        this.receiptExampleService = receiptExampleService;
        this.receiptExampleQueryService = receiptExampleQueryService;
    }

    /**
     * {@code POST  /receipt-examples} : Create a new receiptExample.
     *
     * @param receiptExampleDTO the receiptExampleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new receiptExampleDTO, or with status {@code 400 (Bad Request)} if the receiptExample has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/receipt-examples")
    public ResponseEntity<ReceiptExampleDTO> createReceiptExample(@RequestBody ReceiptExampleDTO receiptExampleDTO) throws URISyntaxException {
        log.debug("REST request to save ReceiptExample : {}", receiptExampleDTO);
        if (receiptExampleDTO.getId() != null) {
            throw new BadRequestAlertException("A new receiptExample cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReceiptExampleDTO result = receiptExampleService.save(receiptExampleDTO);
        return ResponseEntity.created(new URI("/api/receipt-examples/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /receipt-examples} : Updates an existing receiptExample.
     *
     * @param receiptExampleDTO the receiptExampleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated receiptExampleDTO,
     * or with status {@code 400 (Bad Request)} if the receiptExampleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the receiptExampleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/receipt-examples")
    public ResponseEntity<ReceiptExampleDTO> updateReceiptExample(@RequestBody ReceiptExampleDTO receiptExampleDTO) throws URISyntaxException {
        log.debug("REST request to update ReceiptExample : {}", receiptExampleDTO);
        if (receiptExampleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReceiptExampleDTO result = receiptExampleService.save(receiptExampleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, receiptExampleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /receipt-examples} : get all the receiptExamples.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of receiptExamples in body.
     */
    @GetMapping("/receipt-examples")
    public ResponseEntity<List<ReceiptExampleDTO>> getAllReceiptExamples(ReceiptExampleCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ReceiptExamples by criteria: {}", criteria);
        Page<ReceiptExampleDTO> page = receiptExampleQueryService.findByCriteria(criteria, pageable);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.path("/api/receipt-examples"), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /receipt-examples/count} : count all the receiptExamples.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/receipt-examples/count")
    public ResponseEntity<Long> countReceiptExamples(ReceiptExampleCriteria criteria) {
        log.debug("REST request to count ReceiptExamples by criteria: {}", criteria);
        return ResponseEntity.ok().body(receiptExampleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /receipt-examples/:id} : get the "id" receiptExample.
     *
     * @param id the id of the receiptExampleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the receiptExampleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/receipt-examples/{id}")
    public ResponseEntity<ReceiptExampleDTO> getReceiptExample(@PathVariable Long id) {
        log.debug("REST request to get ReceiptExample : {}", id);
        Optional<ReceiptExampleDTO> receiptExampleDTO = receiptExampleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(receiptExampleDTO);
    }

    /**
     * {@code DELETE  /receipt-examples/:id} : delete the "id" receiptExample.
     *
     * @param id the id of the receiptExampleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/receipt-examples/{id}")
    public ResponseEntity<Void> deleteReceiptExample(@PathVariable Long id) {
        log.debug("REST request to delete ReceiptExample : {}", id);
        receiptExampleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
