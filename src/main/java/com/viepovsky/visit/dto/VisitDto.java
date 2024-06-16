package com.viepovsky.visit.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record VisitDto(
        Long id,
        LocalDate visitStartDate,
        LocalTime visitStartTime,
        LocalDate visitEndDate,
        LocalTime visitEndTime) {}
