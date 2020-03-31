package com.globio.peopledirectory.repository;

import com.globio.peopledirectory.domain.Name;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Name entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NameRepository extends JpaRepository<Name, Long> {
}
