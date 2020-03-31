package com.globio.peopledirectory.service.impl;

import com.globio.peopledirectory.service.NameService;
import com.globio.peopledirectory.domain.Name;
import com.globio.peopledirectory.repository.NameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Name}.
 */
@Service
@Transactional
public class NameServiceImpl implements NameService {

    private final Logger log = LoggerFactory.getLogger(NameServiceImpl.class);

    private final NameRepository nameRepository;

    public NameServiceImpl(NameRepository nameRepository) {
        this.nameRepository = nameRepository;
    }

    /**
     * Save a name.
     *
     * @param name the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Name save(Name name) {
        log.debug("Request to save Name : {}", name);
        return nameRepository.save(name);
    }

    /**
     * Get all the names.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Name> findAll() {
        log.debug("Request to get all Names");
        return nameRepository.findAll();
    }

    /**
     * Get one name by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Name> findOne(Long id) {
        log.debug("Request to get Name : {}", id);
        return nameRepository.findById(id);
    }

    /**
     * Delete the name by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Name : {}", id);
        nameRepository.deleteById(id);
    }
}
