package com.viepovsky.garage.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Builder;

import java.util.List;

@Builder
public record GarageDto(
        Long id, @NotBlank String name, @NotBlank String address, List<ScheduleDto> schedules) {}
