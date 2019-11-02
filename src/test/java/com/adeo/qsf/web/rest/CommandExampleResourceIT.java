package com.adeo.qsf.web.rest;

import com.adeo.qsf.Qsf2App;
import com.adeo.qsf.domain.CommandExample;
import com.adeo.qsf.domain.ReceiptExample;
import com.adeo.qsf.repository.CommandExampleRepository;
import com.adeo.qsf.service.CommandExampleService;
import com.adeo.qsf.service.dto.CommandExampleDTO;
import com.adeo.qsf.service.mapper.CommandExampleMapper;
import com.adeo.qsf.web.rest.errors.ExceptionTranslator;
import com.adeo.qsf.service.dto.CommandExampleCriteria;
import com.adeo.qsf.service.CommandExampleQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.adeo.qsf.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link CommandExampleResource} REST controller.
 */
@SpringBootTest(classes = Qsf2App.class)
public class CommandExampleResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CommandExampleRepository commandExampleRepository;

    @Autowired
    private CommandExampleMapper commandExampleMapper;

    @Autowired
    private CommandExampleService commandExampleService;

    @Autowired
    private CommandExampleQueryService commandExampleQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCommandExampleMockMvc;

    private CommandExample commandExample;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommandExampleResource commandExampleResource = new CommandExampleResource(commandExampleService, commandExampleQueryService);
        this.restCommandExampleMockMvc = MockMvcBuilders.standaloneSetup(commandExampleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommandExample createEntity(EntityManager em) {
        CommandExample commandExample = new CommandExample()
            .description(DEFAULT_DESCRIPTION);
        return commandExample;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommandExample createUpdatedEntity(EntityManager em) {
        CommandExample commandExample = new CommandExample()
            .description(UPDATED_DESCRIPTION);
        return commandExample;
    }

    @BeforeEach
    public void initTest() {
        commandExample = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommandExample() throws Exception {
        int databaseSizeBeforeCreate = commandExampleRepository.findAll().size();

        // Create the CommandExample
        CommandExampleDTO commandExampleDTO = commandExampleMapper.toDto(commandExample);
        restCommandExampleMockMvc.perform(post("/api/command-examples")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commandExampleDTO)))
            .andExpect(status().isCreated());

        // Validate the CommandExample in the database
        List<CommandExample> commandExampleList = commandExampleRepository.findAll();
        assertThat(commandExampleList).hasSize(databaseSizeBeforeCreate + 1);
        CommandExample testCommandExample = commandExampleList.get(commandExampleList.size() - 1);
        assertThat(testCommandExample.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCommandExampleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commandExampleRepository.findAll().size();

        // Create the CommandExample with an existing ID
        commandExample.setId(1L);
        CommandExampleDTO commandExampleDTO = commandExampleMapper.toDto(commandExample);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommandExampleMockMvc.perform(post("/api/command-examples")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commandExampleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommandExample in the database
        List<CommandExample> commandExampleList = commandExampleRepository.findAll();
        assertThat(commandExampleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCommandExamples() throws Exception {
        // Initialize the database
        commandExampleRepository.saveAndFlush(commandExample);

        // Get all the commandExampleList
        restCommandExampleMockMvc.perform(get("/api/command-examples?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commandExample.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getCommandExample() throws Exception {
        // Initialize the database
        commandExampleRepository.saveAndFlush(commandExample);

        // Get the commandExample
        restCommandExampleMockMvc.perform(get("/api/command-examples/{id}", commandExample.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commandExample.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllCommandExamplesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        commandExampleRepository.saveAndFlush(commandExample);

        // Get all the commandExampleList where description equals to DEFAULT_DESCRIPTION
        defaultCommandExampleShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the commandExampleList where description equals to UPDATED_DESCRIPTION
        defaultCommandExampleShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCommandExamplesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        commandExampleRepository.saveAndFlush(commandExample);

        // Get all the commandExampleList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCommandExampleShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the commandExampleList where description equals to UPDATED_DESCRIPTION
        defaultCommandExampleShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCommandExamplesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandExampleRepository.saveAndFlush(commandExample);

        // Get all the commandExampleList where description is not null
        defaultCommandExampleShouldBeFound("description.specified=true");

        // Get all the commandExampleList where description is null
        defaultCommandExampleShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommandExamplesByReceiptExampleIsEqualToSomething() throws Exception {
        // Initialize the database
        ReceiptExample receiptExample = ReceiptExampleResourceIT.createEntity(em);
        em.persist(receiptExample);
        em.flush();
        commandExample.setReceiptExample(receiptExample);
        commandExampleRepository.saveAndFlush(commandExample);
        Long receiptExampleId = receiptExample.getId();

        // Get all the commandExampleList where receiptExample equals to receiptExampleId
        defaultCommandExampleShouldBeFound("receiptExampleId.equals=" + receiptExampleId);

        // Get all the commandExampleList where receiptExample equals to receiptExampleId + 1
        defaultCommandExampleShouldNotBeFound("receiptExampleId.equals=" + (receiptExampleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCommandExampleShouldBeFound(String filter) throws Exception {
        restCommandExampleMockMvc.perform(get("/api/command-examples?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commandExample.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restCommandExampleMockMvc.perform(get("/api/command-examples/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCommandExampleShouldNotBeFound(String filter) throws Exception {
        restCommandExampleMockMvc.perform(get("/api/command-examples?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommandExampleMockMvc.perform(get("/api/command-examples/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCommandExample() throws Exception {
        // Get the commandExample
        restCommandExampleMockMvc.perform(get("/api/command-examples/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommandExample() throws Exception {
        // Initialize the database
        commandExampleRepository.saveAndFlush(commandExample);

        int databaseSizeBeforeUpdate = commandExampleRepository.findAll().size();

        // Update the commandExample
        CommandExample updatedCommandExample = commandExampleRepository.findById(commandExample.getId()).get();
        // Disconnect from session so that the updates on updatedCommandExample are not directly saved in db
        em.detach(updatedCommandExample);
        updatedCommandExample
            .description(UPDATED_DESCRIPTION);
        CommandExampleDTO commandExampleDTO = commandExampleMapper.toDto(updatedCommandExample);

        restCommandExampleMockMvc.perform(put("/api/command-examples")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commandExampleDTO)))
            .andExpect(status().isOk());

        // Validate the CommandExample in the database
        List<CommandExample> commandExampleList = commandExampleRepository.findAll();
        assertThat(commandExampleList).hasSize(databaseSizeBeforeUpdate);
        CommandExample testCommandExample = commandExampleList.get(commandExampleList.size() - 1);
        assertThat(testCommandExample.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCommandExample() throws Exception {
        int databaseSizeBeforeUpdate = commandExampleRepository.findAll().size();

        // Create the CommandExample
        CommandExampleDTO commandExampleDTO = commandExampleMapper.toDto(commandExample);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommandExampleMockMvc.perform(put("/api/command-examples")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commandExampleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommandExample in the database
        List<CommandExample> commandExampleList = commandExampleRepository.findAll();
        assertThat(commandExampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommandExample() throws Exception {
        // Initialize the database
        commandExampleRepository.saveAndFlush(commandExample);

        int databaseSizeBeforeDelete = commandExampleRepository.findAll().size();

        // Delete the commandExample
        restCommandExampleMockMvc.perform(delete("/api/command-examples/{id}", commandExample.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<CommandExample> commandExampleList = commandExampleRepository.findAll();
        assertThat(commandExampleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommandExample.class);
        CommandExample commandExample1 = new CommandExample();
        commandExample1.setId(1L);
        CommandExample commandExample2 = new CommandExample();
        commandExample2.setId(commandExample1.getId());
        assertThat(commandExample1).isEqualTo(commandExample2);
        commandExample2.setId(2L);
        assertThat(commandExample1).isNotEqualTo(commandExample2);
        commandExample1.setId(null);
        assertThat(commandExample1).isNotEqualTo(commandExample2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommandExampleDTO.class);
        CommandExampleDTO commandExampleDTO1 = new CommandExampleDTO();
        commandExampleDTO1.setId(1L);
        CommandExampleDTO commandExampleDTO2 = new CommandExampleDTO();
        assertThat(commandExampleDTO1).isNotEqualTo(commandExampleDTO2);
        commandExampleDTO2.setId(commandExampleDTO1.getId());
        assertThat(commandExampleDTO1).isEqualTo(commandExampleDTO2);
        commandExampleDTO2.setId(2L);
        assertThat(commandExampleDTO1).isNotEqualTo(commandExampleDTO2);
        commandExampleDTO1.setId(null);
        assertThat(commandExampleDTO1).isNotEqualTo(commandExampleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commandExampleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commandExampleMapper.fromId(null)).isNull();
    }
}
