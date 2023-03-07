package com.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class BookingDto {
    private Long id;
    private String status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime created;
    private BigDecimal totalCost;
    private List<CarServiceDto> carServiceDtoList;
}
