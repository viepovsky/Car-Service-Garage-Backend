package com.viepovsky.vehicle;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarMapper {

    public CarDto mapToCarDto(Vehicle car) {
        //TODO fix this
        return null;
//        return new CarDto(
//                car.getId(),
//                car.getMake(),
//                car.getModel(),
//                car.getType(),
//                car.getManufactured_year(),
//                car.getEngine(),
//                car.getUser().getId()
//        );
    }

    public Vehicle mapToCar(CarDto carDto) {
        //TODO fix this
        return null;
//        return new Vehicle(
//                carDto.getId(),
//                carDto.getModel(),
//                carDto.getYear()
//        );
    }

    public List<CarDto> mapToCarDtoList(List<Vehicle> carList) {
        return carList.stream()
                .map(this::mapToCarDto)
                .toList();
    }
}
