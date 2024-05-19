package com.viepovsky.garage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Builder;

@Builder
public record GarageCreateRequest(
        @NotBlank String name,
        @NotBlank String description,
        @NotNull AddressCreateRequest address) {}
