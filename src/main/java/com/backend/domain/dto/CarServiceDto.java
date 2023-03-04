package com.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Time;

@Data
@AllArgsConstructor
public class CarServiceDto {
    private Long id;
    private String name;
    private String description;
    private Long cost;
    private Time repairTime;
}
