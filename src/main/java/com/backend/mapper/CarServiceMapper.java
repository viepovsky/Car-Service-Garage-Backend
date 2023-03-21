package com.backend.mapper;

import com.backend.domain.CarService;
import com.backend.domain.dto.CarServiceDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CarServiceMapper {
    private CarMapper carMapper;
    private BookingMapper bookingMapper;

    public CarServiceDto mapToCarServiceDto(CarService carService) {
        return new CarServiceDto (
                carService.getId(),
                carService.getName(),
                carService.getDescription(),
                carService.getCost(),
                carService.getRepairTimeInMinutes()
        );
    }



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
