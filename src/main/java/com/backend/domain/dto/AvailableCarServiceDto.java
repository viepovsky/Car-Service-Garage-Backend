package com.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class AvailableCarServiceDto {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private BigDecimal cost;

    @NotNull
    private int repairTimeInMinutes;

    @NotBlank
    private String premiumMakes;

    @NotNull
    private BigDecimal makeMultiplier;
}
