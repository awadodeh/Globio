package com.globio.peopledirectory.service.impl;

import com.globio.peopledirectory.service.SettingService;
import com.globio.peopledirectory.domain.Setting;
import com.globio.peopledirectory.repository.SettingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Setting}.
 */
@Service
@Transactional
public class SettingServiceImpl implements SettingService {

    private final Logger log = LoggerFactory.getLogger(SettingServiceImpl.class);

    private final SettingRepository settingRepository;

    public SettingServiceImpl(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    /**
     * Save a setting.
     *
     * @param setting the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Setting save(Setting setting) {
        log.debug("Request to save Setting : {}", setting);
        return settingRepository.save(setting);
    }

    /**
     * Get all the settings.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Setting> findAll() {
        log.debug("Request to get all Settings");
        return settingRepository.findAll();
    }

    /**
     * Get one setting by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Setting> findOne(Long id) {
        log.debug("Request to get Setting : {}", id);
        return settingRepository.findById(id);
    }

    /**
     * Delete the setting by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Setting : {}", id);
        settingRepository.deleteById(id);
    }
}
