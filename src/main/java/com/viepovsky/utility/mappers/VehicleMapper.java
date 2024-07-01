package com.viepovsky.utility.mappers;

import com.viepovsky.vehicle.dto.*;
import com.viepovsky.vehicle.model.*;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleMapper {

    public MakeDto toMakeDto(Make make) {
        return new MakeDto(make.getId(), make.getName());
    }

    public List<MakeDto> toMakeDtoList(List<Make> makes) {
        return makes.stream().map(this::toMakeDto).toList();
    }

    public ModelDto toModelDto(Model model) {
        return new ModelDto(
                model.getId(), model.getName(), toMakeDto(model.getMake()), model.getType().name());
    }

    public List<ModelDto> toModelDtoList(List<Model> models) {
        return models.stream().map(this::toModelDto).toList();
    }

    public VehicleDto toVehicleDto(Vehicle vehicle) {
        return new VehicleDto(
                vehicle.getId(),
                vehicle.getUser().getId(),
                vehicle.getVin(),
                vehicle.getLicensePlate(),
                toModelDto(vehicle.getModel()),
                vehicle.getEngineType().name(),
                vehicle.getManufactured_year(),
                vehicle.getDetails());
    }

    public Vehicle toVehicle(VehicleCreateRequest vehicleDto) {
        return Vehicle.builder()
                .vin(vehicleDto.vin())
                .licensePlate(vehicleDto.licensePlate())
                .engineType(EngineType.valueOf(vehicleDto.engineType()))
                .manufactured_year(vehicleDto.manufactured_year())
                .details(vehicleDto.details())
                .build();
    }

    public Vehicle toVehicle(VehicleUpdateRequest vehicleDto) {
        return Vehicle.builder()
                .id(vehicleDto.vehicleId())
                .vin(vehicleDto.vin())
                .licensePlate(vehicleDto.licensePlate())
                .engineType(EngineType.valueOf(vehicleDto.engineType()))
                .manufactured_year(vehicleDto.manufactured_year())
                .details(vehicleDto.details())
                .build();
    }

    public List<VehicleDto> toVehicleDtoList(List<Vehicle> vehicles) {
        return vehicles.stream().map(this::toVehicleDto).toList();
    }
}
