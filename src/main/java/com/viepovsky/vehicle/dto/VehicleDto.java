package com.viepovsky.vehicle.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record VehicleDto(
        Long vehicleId,
        Long userId,
        String vin,
        @NotEmpty String licensePlate,
        @NotNull ModelDto model,
        String engineType,
        int manufactured_year,
        String details) {}
