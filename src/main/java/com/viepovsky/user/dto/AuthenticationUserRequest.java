package com.viepovsky.user.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Builder;

@Builder
public record AuthenticationUserRequest(
        @NotBlank(message = "Username must not be empty") String username,
        @NotBlank(message = "Password must not be empty") String password) {}
