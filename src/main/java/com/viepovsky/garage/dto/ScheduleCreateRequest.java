package com.viepovsky.garage.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record ScheduleCreateRequest(
        @NotNull LocalDate day, @NotNull LocalTime openFrom, @NotNull LocalTime openTill) {}
