package com.viepovsky.garage.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateScheduleRequest(
        @NotNull LocalDate day, @NotNull LocalTime openFrom, @NotNull LocalTime openTill) {}
