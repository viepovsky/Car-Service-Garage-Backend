package com.viepovsky.garage.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record ScheduleCreateRequest(
        @NotNull LocalDate date, @NotNull LocalTime openTime, @NotNull LocalTime closeTime) {}
