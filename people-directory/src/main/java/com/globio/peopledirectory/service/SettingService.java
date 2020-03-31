package com.globio.peopledirectory.service;

import com.globio.peopledirectory.domain.Setting;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Setting}.
 */
public interface SettingService {

    /**
     * Save a setting.
     *
     * @param setting the entity to save.
     * @return the persisted entity.
     */
    Setting save(Setting setting);

    /**
     * Get all the settings.
     *
     * @return the list of entities.
     */
    List<Setting> findAll();

    /**
     * Get the "id" setting.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Setting> findOne(Long id);

    /**
     * Delete the "id" setting.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
