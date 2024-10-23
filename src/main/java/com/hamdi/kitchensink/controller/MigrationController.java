package com.hamdi.kitchensink.controller;

import com.hamdi.kitchensink.migration.MigrationService;
import com.hamdi.kitchensink.model.Migration;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to migrate data from Relational DB to MongoDB
 */
@RestController
@RequestMapping("/migration")
@Tag(name = "MongoDB Migration", description = "MongoDB migration controller")
public class MigrationController {

    private final MigrationService migrationService;

    public MigrationController(MigrationService migrationService) {
        this.migrationService = migrationService;
    }

    @PostMapping
    public Migration migrate() {
        return migrationService.migrateMembers();
    }

}
