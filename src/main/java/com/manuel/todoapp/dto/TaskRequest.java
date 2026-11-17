package com.manuel.todoapp.dto;

import com.manuel.todoapp.model.TaskPriority;
import com.manuel.todoapp.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TaskRequest(
        @NotBlank
        String title,
        String description,
        @NotNull
        TaskStatus status,
        @NotNull
        TaskPriority priority,
        LocalDateTime dueDate
) {}
