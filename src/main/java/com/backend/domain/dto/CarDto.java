package com.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CarDto {
    private Long id;
    private String make;
    private String model;
    private int year;
    private String engine;
}
