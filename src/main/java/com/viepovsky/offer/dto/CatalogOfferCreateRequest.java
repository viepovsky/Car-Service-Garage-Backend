package com.viepovsky.offer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CatalogOfferCreateRequest(
        @NotBlank String name,
        @NotBlank String description,
        @NotNull BigDecimal price,
        @NotNull int probableRepairTime) {}
