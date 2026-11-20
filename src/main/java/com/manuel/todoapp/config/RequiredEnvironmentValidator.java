package com.manuel.todoapp.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.StringUtils;

/**
 * Checks that all required environment variables are set before any bean is created,
 * so a missing variable stops the app with a clear message instead of a database error.
 */
public class RequiredEnvironmentValidator implements EnvironmentPostProcessor {
    private static final List<String> REQUIRED_VARIABLES = List.of("JWT_SECRET", "DB_NAME", "DB_USER", "DB_PASSWORD");
    
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        List<String> missingVariables = new ArrayList<>();

        for (String variable : REQUIRED_VARIABLES) {
            if (!StringUtils.hasText(environment.getProperty(variable))) {
                missingVariables.add(variable);
            }
        }

        if (!missingVariables.isEmpty()) {
            throw new IllegalStateException("Missing required environment variables: " + String.join(", ", missingVariables));
        }
    }
}