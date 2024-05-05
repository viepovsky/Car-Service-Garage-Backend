package com.viepovsky.utility.mapper;

import com.viepovsky.vehicle.dto.ModelDto;
import com.viepovsky.vehicle.dto.VehicleDto;
import com.viepovsky.vehicle.model.Model;
import com.viepovsky.vehicle.model.Vehicle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleMapper {

    public ModelDto toModelDto(Model model) {
        return new ModelDto(
                model.getId(), model.getName(), model.getMake().getName(), model.getType().name());
    }

    public VehicleDto toVehicleDto(Vehicle vehicle) {
        return new VehicleDto(
                vehicle.getId(),
                vehicle.getVin(),
                vehicle.getLicensePlate(),
                vehicle.getUser().getId(),
                toModelDto(vehicle.getModel()),
                vehicle.getEngineType().name(),
                vehicle.getManufactured_year(),
                vehicle.getDetails());
    }

    public Vehicle mapToVehicle(VehicleDto carDto) {
        // TODO fix this
        return null;
        //        return new Vehicle(
        //                carDto.getId(),
        //                carDto.getModel(),
        //                carDto.getYear()
        //        );
    }

    public List<VehicleDto> mapToVehicleDtoList(List<Vehicle> carList) {
        return carList.stream().map(this::toVehicleDto).toList();
    }
}
