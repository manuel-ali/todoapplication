package com.manuel.todoapp.dto;

import com.manuel.todoapp.model.Role;

public record AuthResponse(
        String token,
        String username,
        Role role
) {}