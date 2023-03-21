package com.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    private Long id;
    private String status;
    private LocalDate date;
    private LocalTime startHour;
    private LocalTime endHour;
    private LocalDateTime created;
    private BigDecimal totalCost;
    private List<Long> carServiceDtoIdList;
    private GarageDto garageDto;
}
