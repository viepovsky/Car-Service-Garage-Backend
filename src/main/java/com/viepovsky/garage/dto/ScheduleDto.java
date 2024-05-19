package com.viepovsky.garage.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record ScheduleDto(Long id, LocalDate day, LocalTime openFrom, LocalTime openTill) {}
