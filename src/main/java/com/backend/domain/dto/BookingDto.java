package com.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class BookingDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate created;
    private List<CarServiceDto> carServiceDtoList;
}
