package com.viepovsky.vehicle.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.Builder;

@Builder
public record VehicleUpdateRequest(
        @NotNull @Min(1) Long vehicleId,
        String vin,
        @NotEmpty String licensePlate,
        @NotNull @Min(1) Long modelId,
        String engineType,
        int manufactured_year,
        String details) {}
