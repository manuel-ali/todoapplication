package com.manuel.todoapp.dto;

import com.manuel.todoapp.model.TaskPriority;
import com.manuel.todoapp.model.TaskStatus;

import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        LocalDateTime createdAt,
        LocalDateTime dueDate
) {}