package com.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Time;

@Data
@AllArgsConstructor
public class GarageWorkTimeDto {
    private Long id;
    private String day;
    private Time startHour;
    private Time endHour;
}
