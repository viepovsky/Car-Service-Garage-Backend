package com.viepovsky.user.dto;

import com.viepovsky.user.model.Role;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record UserDto(
        Long id,
        @NotBlank String username,
        String firstName,
        String lastName,
        String companyName,
        @NotBlank String email,
        @NotBlank String mobile,
        String password,
        Role role,
        LocalDateTime createdDate) {
    public UserDto(Long id, String username, String password, Role role) {
        this(id, username, null, null, null, null, null, password, role, null);
    }

    public UserDto(
            Long id,
            String firstName,
            String lastName,
            String email,
            String mobile,
            String username,
            LocalDateTime createdDate) {
        this(id, username, firstName, lastName, null, email, mobile, null, null, createdDate);
    }
}
