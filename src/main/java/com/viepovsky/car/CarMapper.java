package com.viepovsky.car;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarMapper {

    public CarDto mapToCarDto(Vehicle car) {
        return new CarDto(
                car.getId(),
                car.getMake(),
                car.getModel(),
                car.getType(),
                car.getYear(),
                car.getEngine(),
                car.getUser().getId()
        );
    }

    public Vehicle mapToCar(CarDto carDto) {
        return new Vehicle(
                carDto.getId(),
                carDto.getMake(),
                carDto.getModel(),
                carDto.getType(),
                carDto.getYear(),
                carDto.getEngine()
        );
    }

    public List<CarDto> mapToCarDtoList(List<Vehicle> carList) {
        return carList.stream()
                .map(this::mapToCarDto)
                .toList();
    }
}
