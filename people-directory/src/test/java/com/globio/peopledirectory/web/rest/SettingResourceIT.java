package com.globio.peopledirectory.web.rest;

import com.globio.peopledirectory.PeopledirectoryApp;
import com.globio.peopledirectory.domain.Setting;
import com.globio.peopledirectory.repository.SettingRepository;
import com.globio.peopledirectory.service.SettingService;

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
 * Integration tests for the {@link SettingResource} REST controller.
 */
@SpringBootTest(classes = PeopledirectoryApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class SettingResourceIT {

    private static final Boolean DEFAULT_MEMBER_VISIBLE = false;
    private static final Boolean UPDATED_MEMBER_VISIBLE = true;

    private static final Boolean DEFAULT_SELF_ADD = false;
    private static final Boolean UPDATED_SELF_ADD = true;

    private static final Boolean DEFAULT_ALLOW_SUB_GROUP = false;
    private static final Boolean UPDATED_ALLOW_SUB_GROUP = true;

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private SettingService settingService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSettingMockMvc;

    private Setting setting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Setting createEntity(EntityManager em) {
        Setting setting = new Setting()
            .memberVisible(DEFAULT_MEMBER_VISIBLE)
            .selfAdd(DEFAULT_SELF_ADD)
            .allowSubGroup(DEFAULT_ALLOW_SUB_GROUP);
        return setting;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Setting createUpdatedEntity(EntityManager em) {
        Setting setting = new Setting()
            .memberVisible(UPDATED_MEMBER_VISIBLE)
            .selfAdd(UPDATED_SELF_ADD)
            .allowSubGroup(UPDATED_ALLOW_SUB_GROUP);
        return setting;
    }

    @BeforeEach
    public void initTest() {
        setting = createEntity(em);
    }

    @Test
    @Transactional
    public void createSetting() throws Exception {
        int databaseSizeBeforeCreate = settingRepository.findAll().size();

        // Create the Setting
        restSettingMockMvc.perform(post("/api/settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(setting)))
            .andExpect(status().isCreated());

        // Validate the Setting in the database
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeCreate + 1);
        Setting testSetting = settingList.get(settingList.size() - 1);
        assertThat(testSetting.isMemberVisible()).isEqualTo(DEFAULT_MEMBER_VISIBLE);
        assertThat(testSetting.isSelfAdd()).isEqualTo(DEFAULT_SELF_ADD);
        assertThat(testSetting.isAllowSubGroup()).isEqualTo(DEFAULT_ALLOW_SUB_GROUP);
    }

    @Test
    @Transactional
    public void createSettingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = settingRepository.findAll().size();

        // Create the Setting with an existing ID
        setting.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSettingMockMvc.perform(post("/api/settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(setting)))
            .andExpect(status().isBadRequest());

        // Validate the Setting in the database
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSettings() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList
        restSettingMockMvc.perform(get("/api/settings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(setting.getId().intValue())))
            .andExpect(jsonPath("$.[*].memberVisible").value(hasItem(DEFAULT_MEMBER_VISIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].selfAdd").value(hasItem(DEFAULT_SELF_ADD.booleanValue())))
            .andExpect(jsonPath("$.[*].allowSubGroup").value(hasItem(DEFAULT_ALLOW_SUB_GROUP.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getSetting() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get the setting
        restSettingMockMvc.perform(get("/api/settings/{id}", setting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(setting.getId().intValue()))
            .andExpect(jsonPath("$.memberVisible").value(DEFAULT_MEMBER_VISIBLE.booleanValue()))
            .andExpect(jsonPath("$.selfAdd").value(DEFAULT_SELF_ADD.booleanValue()))
            .andExpect(jsonPath("$.allowSubGroup").value(DEFAULT_ALLOW_SUB_GROUP.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSetting() throws Exception {
        // Get the setting
        restSettingMockMvc.perform(get("/api/settings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSetting() throws Exception {
        // Initialize the database
        settingService.save(setting);

        int databaseSizeBeforeUpdate = settingRepository.findAll().size();

        // Update the setting
        Setting updatedSetting = settingRepository.findById(setting.getId()).get();
        // Disconnect from session so that the updates on updatedSetting are not directly saved in db
        em.detach(updatedSetting);
        updatedSetting
            .memberVisible(UPDATED_MEMBER_VISIBLE)
            .selfAdd(UPDATED_SELF_ADD)
            .allowSubGroup(UPDATED_ALLOW_SUB_GROUP);

        restSettingMockMvc.perform(put("/api/settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSetting)))
            .andExpect(status().isOk());

        // Validate the Setting in the database
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeUpdate);
        Setting testSetting = settingList.get(settingList.size() - 1);
        assertThat(testSetting.isMemberVisible()).isEqualTo(UPDATED_MEMBER_VISIBLE);
        assertThat(testSetting.isSelfAdd()).isEqualTo(UPDATED_SELF_ADD);
        assertThat(testSetting.isAllowSubGroup()).isEqualTo(UPDATED_ALLOW_SUB_GROUP);
    }

    @Test
    @Transactional
    public void updateNonExistingSetting() throws Exception {
        int databaseSizeBeforeUpdate = settingRepository.findAll().size();

        // Create the Setting

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSettingMockMvc.perform(put("/api/settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(setting)))
            .andExpect(status().isBadRequest());

        // Validate the Setting in the database
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSetting() throws Exception {
        // Initialize the database
        settingService.save(setting);

        int databaseSizeBeforeDelete = settingRepository.findAll().size();

        // Delete the setting
        restSettingMockMvc.perform(delete("/api/settings/{id}", setting.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
