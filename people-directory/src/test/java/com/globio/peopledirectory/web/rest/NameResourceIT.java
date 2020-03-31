package com.globio.peopledirectory.web.rest;

import com.globio.peopledirectory.PeopledirectoryApp;
import com.globio.peopledirectory.domain.Name;
import com.globio.peopledirectory.repository.NameRepository;
import com.globio.peopledirectory.service.NameService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link NameResource} REST controller.
 */
@SpringBootTest(classes = PeopledirectoryApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class NameResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PREFERED_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PREFERED_NAME = "BBBBBBBBBB";

    @Autowired
    private NameRepository nameRepository;

    @Autowired
    private NameService nameService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNameMockMvc;

    private Name name;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Name createEntity(EntityManager em) {
        Name name = new Name()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .preferedName(DEFAULT_PREFERED_NAME);
        return name;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Name createUpdatedEntity(EntityManager em) {
        Name name = new Name()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .preferedName(UPDATED_PREFERED_NAME);
        return name;
    }

    @BeforeEach
    public void initTest() {
        name = createEntity(em);
    }

    @Test
    @Transactional
    public void createName() throws Exception {
        int databaseSizeBeforeCreate = nameRepository.findAll().size();

        // Create the Name
        restNameMockMvc.perform(post("/api/names")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(name)))
            .andExpect(status().isCreated());

        // Validate the Name in the database
        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeCreate + 1);
        Name testName = nameList.get(nameList.size() - 1);
        assertThat(testName.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testName.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testName.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testName.getPreferedName()).isEqualTo(DEFAULT_PREFERED_NAME);
    }

    @Test
    @Transactional
    public void createNameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nameRepository.findAll().size();

        // Create the Name with an existing ID
        name.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNameMockMvc.perform(post("/api/names")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(name)))
            .andExpect(status().isBadRequest());

        // Validate the Name in the database
        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNames() throws Exception {
        // Initialize the database
        nameRepository.saveAndFlush(name);

        // Get all the nameList
        restNameMockMvc.perform(get("/api/names?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(name.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].preferedName").value(hasItem(DEFAULT_PREFERED_NAME)));
    }
    
    @Test
    @Transactional
    public void getName() throws Exception {
        // Initialize the database
        nameRepository.saveAndFlush(name);

        // Get the name
        restNameMockMvc.perform(get("/api/names/{id}", name.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(name.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.preferedName").value(DEFAULT_PREFERED_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingName() throws Exception {
        // Get the name
        restNameMockMvc.perform(get("/api/names/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateName() throws Exception {
        // Initialize the database
        nameService.save(name);

        int databaseSizeBeforeUpdate = nameRepository.findAll().size();

        // Update the name
        Name updatedName = nameRepository.findById(name.getId()).get();
        // Disconnect from session so that the updates on updatedName are not directly saved in db
        em.detach(updatedName);
        updatedName
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .preferedName(UPDATED_PREFERED_NAME);

        restNameMockMvc.perform(put("/api/names")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedName)))
            .andExpect(status().isOk());

        // Validate the Name in the database
        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeUpdate);
        Name testName = nameList.get(nameList.size() - 1);
        assertThat(testName.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testName.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testName.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testName.getPreferedName()).isEqualTo(UPDATED_PREFERED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingName() throws Exception {
        int databaseSizeBeforeUpdate = nameRepository.findAll().size();

        // Create the Name

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNameMockMvc.perform(put("/api/names")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(name)))
            .andExpect(status().isBadRequest());

        // Validate the Name in the database
        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteName() throws Exception {
        // Initialize the database
        nameService.save(name);

        int databaseSizeBeforeDelete = nameRepository.findAll().size();

        // Delete the name
        restNameMockMvc.perform(delete("/api/names/{id}", name.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
