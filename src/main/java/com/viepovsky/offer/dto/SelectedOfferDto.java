package com.viepovsky.offer.dto;

import java.math.BigDecimal;

public record SelectedOfferDto(
        Long id,
        BigDecimal price,
        BigDecimal discount,
        int probableRepairTime,
        String status,
        String details,
        Long catalogOfferId,
        Long visitId) {}
