package com.adeo.qsf.service;

import com.adeo.qsf.domain.CommandExample;
import com.adeo.qsf.repository.CommandExampleRepository;
import com.adeo.qsf.service.dto.CommandExampleDTO;
import com.adeo.qsf.service.mapper.CommandExampleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CommandExample}.
 */
@Service
@Transactional
public class CommandExampleService {

    private final Logger log = LoggerFactory.getLogger(CommandExampleService.class);

    private final CommandExampleRepository commandExampleRepository;

    private final CommandExampleMapper commandExampleMapper;

    public CommandExampleService(CommandExampleRepository commandExampleRepository, CommandExampleMapper commandExampleMapper) {
        this.commandExampleRepository = commandExampleRepository;
        this.commandExampleMapper = commandExampleMapper;
    }

    /**
     * Save a commandExample.
     *
     * @param commandExampleDTO the entity to save.
     * @return the persisted entity.
     */
    public CommandExampleDTO save(CommandExampleDTO commandExampleDTO) {
        log.debug("Request to save CommandExample : {}", commandExampleDTO);
        CommandExample commandExample = commandExampleMapper.toEntity(commandExampleDTO);
        commandExample = commandExampleRepository.save(commandExample);
        return commandExampleMapper.toDto(commandExample);
    }

    /**
     * Get all the commandExamples.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CommandExampleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommandExamples");
        return commandExampleRepository.findAll(pageable)
            .map(commandExampleMapper::toDto);
    }


    /**
     * Get one commandExample by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommandExampleDTO> findOne(Long id) {
        log.debug("Request to get CommandExample : {}", id);
        return commandExampleRepository.findById(id)
            .map(commandExampleMapper::toDto);
    }

    /**
     * Delete the commandExample by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CommandExample : {}", id);
        commandExampleRepository.deleteById(id);
    }
}
