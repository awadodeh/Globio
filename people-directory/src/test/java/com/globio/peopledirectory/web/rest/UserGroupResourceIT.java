package com.globio.peopledirectory.web.rest;

import com.globio.peopledirectory.PeopledirectoryApp;
import com.globio.peopledirectory.domain.UserGroup;
import com.globio.peopledirectory.repository.UserGroupRepository;
import com.globio.peopledirectory.service.UserGroupService;

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
 * Integration tests for the {@link UserGroupResource} REST controller.
 */
@SpringBootTest(classes = PeopledirectoryApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class UserGroupResourceIT {

    private static final String DEFAULT_GROUP_ID = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_ID = "BBBBBBBBBB";

    private static final String DEFAULT_GROUP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GROUP_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_GROUP_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_OWNER = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_UPDATED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPDATED = "BBBBBBBBBB";

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserGroupMockMvc;

    private UserGroup userGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserGroup createEntity(EntityManager em) {
        UserGroup userGroup = new UserGroup()
            .groupID(DEFAULT_GROUP_ID)
            .groupName(DEFAULT_GROUP_NAME)
            .groupDescription(DEFAULT_GROUP_DESCRIPTION)
            .groupOwner(DEFAULT_GROUP_OWNER)
            .lastUpdated(DEFAULT_LAST_UPDATED);
        return userGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserGroup createUpdatedEntity(EntityManager em) {
        UserGroup userGroup = new UserGroup()
            .groupID(UPDATED_GROUP_ID)
            .groupName(UPDATED_GROUP_NAME)
            .groupDescription(UPDATED_GROUP_DESCRIPTION)
            .groupOwner(UPDATED_GROUP_OWNER)
            .lastUpdated(UPDATED_LAST_UPDATED);
        return userGroup;
    }

    @BeforeEach
    public void initTest() {
        userGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserGroup() throws Exception {
        int databaseSizeBeforeCreate = userGroupRepository.findAll().size();

        // Create the UserGroup
        restUserGroupMockMvc.perform(post("/api/user-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userGroup)))
            .andExpect(status().isCreated());

        // Validate the UserGroup in the database
        List<UserGroup> userGroupList = userGroupRepository.findAll();
        assertThat(userGroupList).hasSize(databaseSizeBeforeCreate + 1);
        UserGroup testUserGroup = userGroupList.get(userGroupList.size() - 1);
        assertThat(testUserGroup.getGroupID()).isEqualTo(DEFAULT_GROUP_ID);
        assertThat(testUserGroup.getGroupName()).isEqualTo(DEFAULT_GROUP_NAME);
        assertThat(testUserGroup.getGroupDescription()).isEqualTo(DEFAULT_GROUP_DESCRIPTION);
        assertThat(testUserGroup.getGroupOwner()).isEqualTo(DEFAULT_GROUP_OWNER);
        assertThat(testUserGroup.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    public void createUserGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userGroupRepository.findAll().size();

        // Create the UserGroup with an existing ID
        userGroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserGroupMockMvc.perform(post("/api/user-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userGroup)))
            .andExpect(status().isBadRequest());

        // Validate the UserGroup in the database
        List<UserGroup> userGroupList = userGroupRepository.findAll();
        assertThat(userGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserGroups() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        // Get all the userGroupList
        restUserGroupMockMvc.perform(get("/api/user-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupID").value(hasItem(DEFAULT_GROUP_ID)))
            .andExpect(jsonPath("$.[*].groupName").value(hasItem(DEFAULT_GROUP_NAME)))
            .andExpect(jsonPath("$.[*].groupDescription").value(hasItem(DEFAULT_GROUP_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].groupOwner").value(hasItem(DEFAULT_GROUP_OWNER)))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED)));
    }
    
    @Test
    @Transactional
    public void getUserGroup() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        // Get the userGroup
        restUserGroupMockMvc.perform(get("/api/user-groups/{id}", userGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userGroup.getId().intValue()))
            .andExpect(jsonPath("$.groupID").value(DEFAULT_GROUP_ID))
            .andExpect(jsonPath("$.groupName").value(DEFAULT_GROUP_NAME))
            .andExpect(jsonPath("$.groupDescription").value(DEFAULT_GROUP_DESCRIPTION))
            .andExpect(jsonPath("$.groupOwner").value(DEFAULT_GROUP_OWNER))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED));
    }

    @Test
    @Transactional
    public void getNonExistingUserGroup() throws Exception {
        // Get the userGroup
        restUserGroupMockMvc.perform(get("/api/user-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserGroup() throws Exception {
        // Initialize the database
        userGroupService.save(userGroup);

        int databaseSizeBeforeUpdate = userGroupRepository.findAll().size();

        // Update the userGroup
        UserGroup updatedUserGroup = userGroupRepository.findById(userGroup.getId()).get();
        // Disconnect from session so that the updates on updatedUserGroup are not directly saved in db
        em.detach(updatedUserGroup);
        updatedUserGroup
            .groupID(UPDATED_GROUP_ID)
            .groupName(UPDATED_GROUP_NAME)
            .groupDescription(UPDATED_GROUP_DESCRIPTION)
            .groupOwner(UPDATED_GROUP_OWNER)
            .lastUpdated(UPDATED_LAST_UPDATED);

        restUserGroupMockMvc.perform(put("/api/user-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserGroup)))
            .andExpect(status().isOk());

        // Validate the UserGroup in the database
        List<UserGroup> userGroupList = userGroupRepository.findAll();
        assertThat(userGroupList).hasSize(databaseSizeBeforeUpdate);
        UserGroup testUserGroup = userGroupList.get(userGroupList.size() - 1);
        assertThat(testUserGroup.getGroupID()).isEqualTo(UPDATED_GROUP_ID);
        assertThat(testUserGroup.getGroupName()).isEqualTo(UPDATED_GROUP_NAME);
        assertThat(testUserGroup.getGroupDescription()).isEqualTo(UPDATED_GROUP_DESCRIPTION);
        assertThat(testUserGroup.getGroupOwner()).isEqualTo(UPDATED_GROUP_OWNER);
        assertThat(testUserGroup.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingUserGroup() throws Exception {
        int databaseSizeBeforeUpdate = userGroupRepository.findAll().size();

        // Create the UserGroup

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserGroupMockMvc.perform(put("/api/user-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userGroup)))
            .andExpect(status().isBadRequest());

        // Validate the UserGroup in the database
        List<UserGroup> userGroupList = userGroupRepository.findAll();
        assertThat(userGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserGroup() throws Exception {
        // Initialize the database
        userGroupService.save(userGroup);

        int databaseSizeBeforeDelete = userGroupRepository.findAll().size();

        // Delete the userGroup
        restUserGroupMockMvc.perform(delete("/api/user-groups/{id}", userGroup.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserGroup> userGroupList = userGroupRepository.findAll();
        assertThat(userGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
