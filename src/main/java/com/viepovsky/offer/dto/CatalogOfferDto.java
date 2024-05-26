package com.viepovsky.offer.dto;

import java.math.BigDecimal;

public record CatalogOfferDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        int probableRepairTime,
        Long garageId) {}
