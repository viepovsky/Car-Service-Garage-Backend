package com.viepovsky.car_repair;

import com.viepovsky.booking.BookingDto;
import com.viepovsky.car.CarDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
class CarRepairDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal cost;
    private int repairTimeInMinutes;
    private CarDto carDto;
    private BookingDto bookingDto;
    private String status;

    public CarRepairDto(Long id, String name, String description, BigDecimal cost, int repairTimeInMinutes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.repairTimeInMinutes = repairTimeInMinutes;
    }
}
