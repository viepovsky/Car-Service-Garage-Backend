package com.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CarServiceDto {
    private Long id;
    private String name;
    private String description;
    private Long cost;
    private int repairTimeInMinutes;
}
