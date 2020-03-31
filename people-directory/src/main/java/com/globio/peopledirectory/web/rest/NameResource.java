package com.globio.peopledirectory.web.rest;

import com.globio.peopledirectory.domain.Name;
import com.globio.peopledirectory.service.NameService;
import com.globio.peopledirectory.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.globio.peopledirectory.domain.Name}.
 */
@RestController
@RequestMapping("/api")
public class NameResource {

    private final Logger log = LoggerFactory.getLogger(NameResource.class);

    private static final String ENTITY_NAME = "name";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NameService nameService;

    public NameResource(NameService nameService) {
        this.nameService = nameService;
    }

    /**
     * {@code POST  /names} : Create a new name.
     *
     * @param name the name to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new name, or with status {@code 400 (Bad Request)} if the name has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/names")
    public ResponseEntity<Name> createName(@RequestBody Name name) throws URISyntaxException {
        log.debug("REST request to save Name : {}", name);
        if (name.getId() != null) {
            throw new BadRequestAlertException("A new name cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Name result = nameService.save(name);
        return ResponseEntity.created(new URI("/api/names/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /names} : Updates an existing name.
     *
     * @param name the name to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated name,
     * or with status {@code 400 (Bad Request)} if the name is not valid,
     * or with status {@code 500 (Internal Server Error)} if the name couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/names")
    public ResponseEntity<Name> updateName(@RequestBody Name name) throws URISyntaxException {
        log.debug("REST request to update Name : {}", name);
        if (name.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Name result = nameService.save(name);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, name.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /names} : get all the names.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of names in body.
     */
    @GetMapping("/names")
    public List<Name> getAllNames() {
        log.debug("REST request to get all Names");
        return nameService.findAll();
    }

    /**
     * {@code GET  /names/:id} : get the "id" name.
     *
     * @param id the id of the name to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the name, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/names/{id}")
    public ResponseEntity<Name> getName(@PathVariable Long id) {
        log.debug("REST request to get Name : {}", id);
        Optional<Name> name = nameService.findOne(id);
        return ResponseUtil.wrapOrNotFound(name);
    }

    /**
     * {@code DELETE  /names/:id} : delete the "id" name.
     *
     * @param id the id of the name to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/names/{id}")
    public ResponseEntity<Void> deleteName(@PathVariable Long id) {
        log.debug("REST request to delete Name : {}", id);
        nameService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
