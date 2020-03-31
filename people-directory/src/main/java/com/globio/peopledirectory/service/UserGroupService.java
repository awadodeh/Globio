package com.globio.peopledirectory.service;

import com.globio.peopledirectory.domain.UserGroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link UserGroup}.
 */
public interface UserGroupService {

    /**
     * Save a userGroup.
     *
     * @param userGroup the entity to save.
     * @return the persisted entity.
     */
    UserGroup save(UserGroup userGroup);

    /**
     * Get all the userGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserGroup> findAll(Pageable pageable);

    /**
     * Get the "id" userGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserGroup> findOne(Long id);

    /**
     * Delete the "id" userGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
