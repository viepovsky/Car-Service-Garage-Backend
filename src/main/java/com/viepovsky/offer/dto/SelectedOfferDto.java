package com.viepovsky.offer.dto;

import com.viepovsky.booking.BookingDto;
import com.viepovsky.vehicle.dto.VehicleDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectedOfferDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal cost;
    private int repairTimeInMinutes;
    private VehicleDto carDto;
    private BookingDto bookingDto;
    private String status;

    public SelectedOfferDto(Long id, String name, String description, BigDecimal cost, int repairTimeInMinutes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.repairTimeInMinutes = repairTimeInMinutes;
    }
}
