package com.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarServiceDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal cost;
    private int repairTimeInMinutes;
    private CarDto carDto;
    private BookingDto bookingDto;
    private String status;

    public CarServiceDto(Long id, String name, String description, BigDecimal cost, int repairTimeInMinutes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.repairTimeInMinutes = repairTimeInMinutes;
    }
}
