package com.viepovsky.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import lombok.Builder;

@Builder
public record RegisterUserRequest(
        @NotBlank(message = "First name must not be empty") String firstName,
        @NotBlank(message = "Last name must not be empty") String lastName,
        @Email(message = "Email is not valid") String email,
        String phoneNumber,
        @NotBlank(message = "Username must not be empty") String username,
        @Pattern(
                        regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W])(?=\\S+$).{8,}",
                        message =
                                "Password should contain at least 8 characters, one uppercase letter, one lowercase letter, and one special character.")
                String password) {}
