package com.viepovsky.vehicle;

import com.viepovsky.security.UserValidator;
import com.viepovsky.utility.mapper.VehicleMapper;
import com.viepovsky.vehicle.dto.VehicleDto;
import com.viepovsky.vehicle.model.Vehicle;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class VehicleFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleFacade.class);
    private final VehicleService vehicleService;
    private final VehicleMapper mapper;
    private final UserValidator userValidator;

    public VehicleDto getVehicle(Long vehicleId) {
        // TODO implement
        return null;
    }

    public List<VehicleDto> getVehiclesByUsername(String username) {
        userValidator.isValidWithAuthToken(username);
        LOGGER.info("Get vehicles for given username endpoint used with username:{}", username);
        List<Vehicle> vehicles = vehicleService.getVehiclesByUsername(username);
        return mapper.mapToVehicleDtoList(vehicles);
    }

    public void createVehicle(VehicleDto vehicleDto, String username) {
        userValidator.isValidWithAuthToken(username);
        LOGGER.info("Create vehicle endpoint used for username:{}", username);
        Vehicle vehicle = mapper.mapToVehicle(vehicleDto);
        vehicleService.saveVehicle(vehicle, username);
    }

    public void updateVehicle(VehicleDto vehicleDto) {
        LOGGER.info("Update vehicle endpoint used for vehicle id:{}", vehicleDto.vehicleId());
        vehicleService.updateVehicle(mapper.mapToVehicle(vehicleDto));
    }

    public void deleteVehicle(Long vehicleId) {
        LOGGER.info("Delete vehicle endpoint used for vehicle id:{}", vehicleId);
        vehicleService.deleteVehicle(vehicleId);
    }
}
