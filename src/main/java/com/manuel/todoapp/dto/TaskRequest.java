package com.manuel.todoapp.dto;

import com.manuel.todoapp.model.TaskPriority;
import com.manuel.todoapp.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TaskRequest(
        @NotBlank(message = "Title is required")
        String title,
        String description,
        @NotNull(message = "Status is required")
        TaskStatus status,
        @NotNull(message = "Priority is required")
        TaskPriority priority,
        LocalDateTime dueDate
) {}
