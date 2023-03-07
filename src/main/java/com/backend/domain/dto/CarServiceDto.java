package com.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CarServiceDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal cost;
    private int repairTimeInMinutes;
}
