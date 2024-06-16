package com.viepovsky.visit.dto;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record VisitFullDetailDto(
        Long id,
        LocalDate visitStartDate,
        LocalTime visitStartTime,
        LocalDate visitEndDate,
        LocalTime visitEndTime,
        String licensePlate,
        BigDecimal totalPrice,
        String status,
        List<Long> selectedOffersIds,
        Long garageId,
        Long vehicleId) {}
