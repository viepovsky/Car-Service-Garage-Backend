package com.viepovsky.garage.availablerepair;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
class AvailableCarRepairDto {
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

    private Long garageId;
}
