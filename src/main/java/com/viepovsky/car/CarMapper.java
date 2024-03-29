package com.viepovsky.car;

import org.springframework.stereotype.Service;

import java.util.List;

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
                car.getUser().getId()
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

    public List<CarDto> mapToCarDtoList(List<Car> carList) {
        return carList.stream()
                .map(this::mapToCarDto)
                .toList();
    }
}
