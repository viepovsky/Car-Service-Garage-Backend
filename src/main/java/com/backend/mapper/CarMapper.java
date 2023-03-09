package com.backend.mapper;

import com.backend.domain.Car;
import com.backend.domain.dto.CarDto;
import org.springframework.stereotype.Service;

@Service
public class CarMapper {

    public CarDto mapToCarDto(Car car) {
        return new CarDto(
                car.getId(),
                car.getMake(),
                car.getModel(),
                car.getType(),
                car.getYear(),
                car.getEngine(),
                car.getCustomer().getId()
        );
    }

    public Car mapToCar(CarDto carDto) {
        return new Car(
                carDto.getId(),
                carDto.getMake(),
                carDto.getModel(),
                carDto.getType(),
                carDto.getYear(),
                carDto.getEngine()
        );
    }
}
