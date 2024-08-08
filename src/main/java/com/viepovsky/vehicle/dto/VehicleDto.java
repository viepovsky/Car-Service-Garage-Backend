package com.viepovsky.vehicle.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record VehicleDto(
        Long vehicleId,
        Long userId,
        String vin,
        @NotEmpty String licensePlate,
        @NotNull ModelDto vehicleModel,
        String engineType,
        Integer manufactured_year,
        String details) {}
