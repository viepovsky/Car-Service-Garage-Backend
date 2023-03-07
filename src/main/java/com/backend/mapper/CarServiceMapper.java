package com.backend.mapper;

import com.backend.domain.CarService;
import com.backend.domain.dto.CarServiceDto;
import org.springframework.stereotype.Service;

@Service
public class CarServiceMapper {

    public CarServiceDto mapToCarServiceDto(CarService carService) {
        return new CarServiceDto (
                carService.getId(),
                carService.getName(),
                carService.getDescription(),
                carService.getCost(),
                carService.getRepairTimeInMinutes()
        );
    }

    public CarService mapToCarService(CarServiceDto carServiceDto) {
        return new CarService(
                carServiceDto.getId(),
                carServiceDto.getName(),
                carServiceDto.getDescription(),
                carServiceDto.getCost(),
                carServiceDto.getRepairTimeInMinutes()
        );
    }
}
