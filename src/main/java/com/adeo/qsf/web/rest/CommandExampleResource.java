package com.adeo.qsf.web.rest;

import com.adeo.qsf.service.CommandExampleService;
import com.adeo.qsf.web.rest.errors.BadRequestAlertException;
import com.adeo.qsf.service.dto.CommandExampleDTO;
import com.adeo.qsf.service.dto.CommandExampleCriteria;
import com.adeo.qsf.service.CommandExampleQueryService;

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
 * REST controller for managing {@link com.adeo.qsf.domain.CommandExample}.
 */
@RestController
@RequestMapping("/api")
public class CommandExampleResource {

    private final Logger log = LoggerFactory.getLogger(CommandExampleResource.class);

    private static final String ENTITY_NAME = "commandExample";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommandExampleService commandExampleService;

    private final CommandExampleQueryService commandExampleQueryService;

    public CommandExampleResource(CommandExampleService commandExampleService, CommandExampleQueryService commandExampleQueryService) {
        this.commandExampleService = commandExampleService;
        this.commandExampleQueryService = commandExampleQueryService;
    }

    /**
     * {@code POST  /command-examples} : Create a new commandExample.
     *
     * @param commandExampleDTO the commandExampleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commandExampleDTO, or with status {@code 400 (Bad Request)} if the commandExample has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/command-examples")
    public ResponseEntity<CommandExampleDTO> createCommandExample(@RequestBody CommandExampleDTO commandExampleDTO) throws URISyntaxException {
        log.debug("REST request to save CommandExample : {}", commandExampleDTO);
        if (commandExampleDTO.getId() != null) {
            throw new BadRequestAlertException("A new commandExample cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommandExampleDTO result = commandExampleService.save(commandExampleDTO);
        return ResponseEntity.created(new URI("/api/command-examples/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /command-examples} : Updates an existing commandExample.
     *
     * @param commandExampleDTO the commandExampleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commandExampleDTO,
     * or with status {@code 400 (Bad Request)} if the commandExampleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commandExampleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/command-examples")
    public ResponseEntity<CommandExampleDTO> updateCommandExample(@RequestBody CommandExampleDTO commandExampleDTO) throws URISyntaxException {
        log.debug("REST request to update CommandExample : {}", commandExampleDTO);
        if (commandExampleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommandExampleDTO result = commandExampleService.save(commandExampleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, commandExampleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /command-examples} : get all the commandExamples.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commandExamples in body.
     */
    @GetMapping("/command-examples")
    public ResponseEntity<List<CommandExampleDTO>> getAllCommandExamples(CommandExampleCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CommandExamples by criteria: {}", criteria);
        Page<CommandExampleDTO> page = commandExampleQueryService.findByCriteria(criteria, pageable);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.path("/api/command-examples"), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /command-examples/count} : count all the commandExamples.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/command-examples/count")
    public ResponseEntity<Long> countCommandExamples(CommandExampleCriteria criteria) {
        log.debug("REST request to count CommandExamples by criteria: {}", criteria);
        return ResponseEntity.ok().body(commandExampleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /command-examples/:id} : get the "id" commandExample.
     *
     * @param id the id of the commandExampleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commandExampleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/command-examples/{id}")
    public ResponseEntity<CommandExampleDTO> getCommandExample(@PathVariable Long id) {
        log.debug("REST request to get CommandExample : {}", id);
        Optional<CommandExampleDTO> commandExampleDTO = commandExampleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commandExampleDTO);
    }

    /**
     * {@code DELETE  /command-examples/:id} : delete the "id" commandExample.
     *
     * @param id the id of the commandExampleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/command-examples/{id}")
    public ResponseEntity<Void> deleteCommandExample(@PathVariable Long id) {
        log.debug("REST request to delete CommandExample : {}", id);
        commandExampleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
