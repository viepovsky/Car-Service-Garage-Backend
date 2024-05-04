package com.viepovsky.garage.dto;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
public class ScheduleDto {

    private Long id;


    private LocalDate date;

    @NotNull
    private LocalTime openFrom;

    @NotNull
    private LocalTime openTill;
}
