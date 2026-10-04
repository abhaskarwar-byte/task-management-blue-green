package com.devops.taskmanagementapi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController {
    private final String version;
    private final String environment;

    public VersionController(
            @Value("${app.version}") String version,
            @Value("${app.environment}") String environment) {
        this.version = version;
        this.environment = environment;
    }

    @GetMapping("/api/version")
    public VersionResponse getVersion() {
        return new VersionResponse(version, environment);
    }

    public record VersionResponse(String version, String environment) {
    }
}