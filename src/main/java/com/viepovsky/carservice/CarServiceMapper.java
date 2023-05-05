package com.viepovsky.carservice;

import com.viepovsky.booking.BookingMapper;
import com.viepovsky.car.CarMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
class CarServiceMapper {
    private CarMapper carMapper;
    private BookingMapper bookingMapper;

    public List<CarServiceDto> mapToCarServiceDtoList(List<CarService> carServiceList) {
        return carServiceList.stream()
                .map(n -> new CarServiceDto(
                        n.getId(),
                        n.getName(),
                        n.getDescription(),
                        n.getCost(),
                        n.getRepairTimeInMinutes(),
                        carMapper.mapToCarDto(n.getCar()),
                        bookingMapper.mapToBookingDto(n.getBooking()),
                        n.getStatus().getServiceStatus()
                ))
                .toList();
    }
}
