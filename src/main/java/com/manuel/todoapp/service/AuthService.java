package com.manuel.todoapp.service;

import com.manuel.todoapp.dto.AuthResponse;
import com.manuel.todoapp.dto.LoginRequest;
import com.manuel.todoapp.dto.RegisterRequest;
import com.manuel.todoapp.exception.DuplicateResourceException;
import com.manuel.todoapp.model.User;
import com.manuel.todoapp.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        String username = request.username();
        String email = request.email();

        if (userService.existsByUsername(username)) {
            throw new DuplicateResourceException("Username already exists");
        }

        if (userService.existsByEmail(email)) {
            throw new DuplicateResourceException("Email already exists");
        }

        String password = passwordEncoder.encode(request.password());

        User user = User.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();

        User createdUser = userService.createUser(user);

        String token = jwtService.generateToken(createdUser);

        return new AuthResponse(token, createdUser.getDisplayUsername(), createdUser.getRole());
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        User user = userService.findUserByEmail(request.email());

        String token = jwtService.generateToken(user);

        return new AuthResponse(token, user.getDisplayUsername(), user.getRole());
    }
}
