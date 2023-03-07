package com.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class GarageWorkTimeDto {
    private Long id;
    private String day;
    private LocalTime startHour;
    private LocalTime endHour;
}
