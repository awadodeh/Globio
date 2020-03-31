package com.globio.peopledirectory.service;

import com.globio.peopledirectory.domain.Name;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Name}.
 */
public interface NameService {

    /**
     * Save a name.
     *
     * @param name the entity to save.
     * @return the persisted entity.
     */
    Name save(Name name);

    /**
     * Get all the names.
     *
     * @return the list of entities.
     */
    List<Name> findAll();

    /**
     * Get the "id" name.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Name> findOne(Long id);

    /**
     * Delete the "id" name.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
