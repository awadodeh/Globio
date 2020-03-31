package com.globio.peopledirectory;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.globio.peopledirectory");

        noClasses()
            .that()
                .resideInAnyPackage("com.globio.peopledirectory.service..")
            .or()
                .resideInAnyPackage("com.globio.peopledirectory.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.globio.peopledirectory.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
