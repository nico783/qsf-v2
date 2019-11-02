package com.adeo.qsf.web.rest;

import com.adeo.qsf.Qsf2App;
import com.adeo.qsf.domain.ReceiptExample;
import com.adeo.qsf.repository.ReceiptExampleRepository;
import com.adeo.qsf.service.ReceiptExampleService;
import com.adeo.qsf.service.dto.ReceiptExampleDTO;
import com.adeo.qsf.service.mapper.ReceiptExampleMapper;
import com.adeo.qsf.web.rest.errors.ExceptionTranslator;
import com.adeo.qsf.service.dto.ReceiptExampleCriteria;
import com.adeo.qsf.service.ReceiptExampleQueryService;

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
 * Integration tests for the {@Link ReceiptExampleResource} REST controller.
 */
@SpringBootTest(classes = Qsf2App.class)
public class ReceiptExampleResourceIT {

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    @Autowired
    private ReceiptExampleRepository receiptExampleRepository;

    @Autowired
    private ReceiptExampleMapper receiptExampleMapper;

    @Autowired
    private ReceiptExampleService receiptExampleService;

    @Autowired
    private ReceiptExampleQueryService receiptExampleQueryService;

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

    private MockMvc restReceiptExampleMockMvc;

    private ReceiptExample receiptExample;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReceiptExampleResource receiptExampleResource = new ReceiptExampleResource(receiptExampleService, receiptExampleQueryService);
        this.restReceiptExampleMockMvc = MockMvcBuilders.standaloneSetup(receiptExampleResource)
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
    public static ReceiptExample createEntity(EntityManager em) {
        ReceiptExample receiptExample = new ReceiptExample()
            .state(DEFAULT_STATE);
        return receiptExample;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReceiptExample createUpdatedEntity(EntityManager em) {
        ReceiptExample receiptExample = new ReceiptExample()
            .state(UPDATED_STATE);
        return receiptExample;
    }

    @BeforeEach
    public void initTest() {
        receiptExample = createEntity(em);
    }

    @Test
    @Transactional
    public void createReceiptExample() throws Exception {
        int databaseSizeBeforeCreate = receiptExampleRepository.findAll().size();

        // Create the ReceiptExample
        ReceiptExampleDTO receiptExampleDTO = receiptExampleMapper.toDto(receiptExample);
        restReceiptExampleMockMvc.perform(post("/api/receipt-examples")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(receiptExampleDTO)))
            .andExpect(status().isCreated());

        // Validate the ReceiptExample in the database
        List<ReceiptExample> receiptExampleList = receiptExampleRepository.findAll();
        assertThat(receiptExampleList).hasSize(databaseSizeBeforeCreate + 1);
        ReceiptExample testReceiptExample = receiptExampleList.get(receiptExampleList.size() - 1);
        assertThat(testReceiptExample.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void createReceiptExampleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = receiptExampleRepository.findAll().size();

        // Create the ReceiptExample with an existing ID
        receiptExample.setId(1L);
        ReceiptExampleDTO receiptExampleDTO = receiptExampleMapper.toDto(receiptExample);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReceiptExampleMockMvc.perform(post("/api/receipt-examples")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(receiptExampleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReceiptExample in the database
        List<ReceiptExample> receiptExampleList = receiptExampleRepository.findAll();
        assertThat(receiptExampleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllReceiptExamples() throws Exception {
        // Initialize the database
        receiptExampleRepository.saveAndFlush(receiptExample);

        // Get all the receiptExampleList
        restReceiptExampleMockMvc.perform(get("/api/receipt-examples?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(receiptExample.getId().intValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }
    
    @Test
    @Transactional
    public void getReceiptExample() throws Exception {
        // Initialize the database
        receiptExampleRepository.saveAndFlush(receiptExample);

        // Get the receiptExample
        restReceiptExampleMockMvc.perform(get("/api/receipt-examples/{id}", receiptExample.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(receiptExample.getId().intValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()));
    }

    @Test
    @Transactional
    public void getAllReceiptExamplesByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        receiptExampleRepository.saveAndFlush(receiptExample);

        // Get all the receiptExampleList where state equals to DEFAULT_STATE
        defaultReceiptExampleShouldBeFound("state.equals=" + DEFAULT_STATE);

        // Get all the receiptExampleList where state equals to UPDATED_STATE
        defaultReceiptExampleShouldNotBeFound("state.equals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllReceiptExamplesByStateIsInShouldWork() throws Exception {
        // Initialize the database
        receiptExampleRepository.saveAndFlush(receiptExample);

        // Get all the receiptExampleList where state in DEFAULT_STATE or UPDATED_STATE
        defaultReceiptExampleShouldBeFound("state.in=" + DEFAULT_STATE + "," + UPDATED_STATE);

        // Get all the receiptExampleList where state equals to UPDATED_STATE
        defaultReceiptExampleShouldNotBeFound("state.in=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllReceiptExamplesByStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        receiptExampleRepository.saveAndFlush(receiptExample);

        // Get all the receiptExampleList where state is not null
        defaultReceiptExampleShouldBeFound("state.specified=true");

        // Get all the receiptExampleList where state is null
        defaultReceiptExampleShouldNotBeFound("state.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultReceiptExampleShouldBeFound(String filter) throws Exception {
        restReceiptExampleMockMvc.perform(get("/api/receipt-examples?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(receiptExample.getId().intValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)));

        // Check, that the count call also returns 1
        restReceiptExampleMockMvc.perform(get("/api/receipt-examples/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultReceiptExampleShouldNotBeFound(String filter) throws Exception {
        restReceiptExampleMockMvc.perform(get("/api/receipt-examples?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReceiptExampleMockMvc.perform(get("/api/receipt-examples/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingReceiptExample() throws Exception {
        // Get the receiptExample
        restReceiptExampleMockMvc.perform(get("/api/receipt-examples/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReceiptExample() throws Exception {
        // Initialize the database
        receiptExampleRepository.saveAndFlush(receiptExample);

        int databaseSizeBeforeUpdate = receiptExampleRepository.findAll().size();

        // Update the receiptExample
        ReceiptExample updatedReceiptExample = receiptExampleRepository.findById(receiptExample.getId()).get();
        // Disconnect from session so that the updates on updatedReceiptExample are not directly saved in db
        em.detach(updatedReceiptExample);
        updatedReceiptExample
            .state(UPDATED_STATE);
        ReceiptExampleDTO receiptExampleDTO = receiptExampleMapper.toDto(updatedReceiptExample);

        restReceiptExampleMockMvc.perform(put("/api/receipt-examples")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(receiptExampleDTO)))
            .andExpect(status().isOk());

        // Validate the ReceiptExample in the database
        List<ReceiptExample> receiptExampleList = receiptExampleRepository.findAll();
        assertThat(receiptExampleList).hasSize(databaseSizeBeforeUpdate);
        ReceiptExample testReceiptExample = receiptExampleList.get(receiptExampleList.size() - 1);
        assertThat(testReceiptExample.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    public void updateNonExistingReceiptExample() throws Exception {
        int databaseSizeBeforeUpdate = receiptExampleRepository.findAll().size();

        // Create the ReceiptExample
        ReceiptExampleDTO receiptExampleDTO = receiptExampleMapper.toDto(receiptExample);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReceiptExampleMockMvc.perform(put("/api/receipt-examples")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(receiptExampleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReceiptExample in the database
        List<ReceiptExample> receiptExampleList = receiptExampleRepository.findAll();
        assertThat(receiptExampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReceiptExample() throws Exception {
        // Initialize the database
        receiptExampleRepository.saveAndFlush(receiptExample);

        int databaseSizeBeforeDelete = receiptExampleRepository.findAll().size();

        // Delete the receiptExample
        restReceiptExampleMockMvc.perform(delete("/api/receipt-examples/{id}", receiptExample.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ReceiptExample> receiptExampleList = receiptExampleRepository.findAll();
        assertThat(receiptExampleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReceiptExample.class);
        ReceiptExample receiptExample1 = new ReceiptExample();
        receiptExample1.setId(1L);
        ReceiptExample receiptExample2 = new ReceiptExample();
        receiptExample2.setId(receiptExample1.getId());
        assertThat(receiptExample1).isEqualTo(receiptExample2);
        receiptExample2.setId(2L);
        assertThat(receiptExample1).isNotEqualTo(receiptExample2);
        receiptExample1.setId(null);
        assertThat(receiptExample1).isNotEqualTo(receiptExample2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReceiptExampleDTO.class);
        ReceiptExampleDTO receiptExampleDTO1 = new ReceiptExampleDTO();
        receiptExampleDTO1.setId(1L);
        ReceiptExampleDTO receiptExampleDTO2 = new ReceiptExampleDTO();
        assertThat(receiptExampleDTO1).isNotEqualTo(receiptExampleDTO2);
        receiptExampleDTO2.setId(receiptExampleDTO1.getId());
        assertThat(receiptExampleDTO1).isEqualTo(receiptExampleDTO2);
        receiptExampleDTO2.setId(2L);
        assertThat(receiptExampleDTO1).isNotEqualTo(receiptExampleDTO2);
        receiptExampleDTO1.setId(null);
        assertThat(receiptExampleDTO1).isNotEqualTo(receiptExampleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(receiptExampleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(receiptExampleMapper.fromId(null)).isNull();
    }
}
