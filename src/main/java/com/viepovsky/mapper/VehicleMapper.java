package com.viepovsky.mapper;

import com.viepovsky.vehicle.dto.VehicleDto;
import com.viepovsky.vehicle.model.Vehicle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleMapper {

    public VehicleDto mapToCarDto(Vehicle car) {
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

    public Vehicle mapToCar(VehicleDto carDto) {
        //TODO fix this
        return null;
//        return new Vehicle(
//                carDto.getId(),
//                carDto.getModel(),
//                carDto.getYear()
//        );
    }

    public List<VehicleDto> mapToCarDtoList(List<Vehicle> carList) {
        return carList.stream()
                .map(this::mapToCarDto)
                .toList();
    }
}
