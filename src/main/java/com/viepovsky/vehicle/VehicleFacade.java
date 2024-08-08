package com.viepovsky.vehicle;

import com.viepovsky.security.DataOwnershipValidator;
import com.viepovsky.utility.mappers.VehicleMapper;
import com.viepovsky.vehicle.dto.*;
import com.viepovsky.vehicle.model.Make;
import com.viepovsky.vehicle.model.Model;
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
    private final DataOwnershipValidator dataOwnershipValidator;

    public VehicleDto getVehicle(Long vehicleId) {
        LOGGER.info("Get vehicle endpoint used for vehicle id:{}", vehicleId);
        Vehicle vehicle = vehicleService.getVehicle(vehicleId);
        dataOwnershipValidator.belongsToAuthenticatedUser(vehicle);
        return mapper.toVehicleDto(vehicle);
    }

    public List<VehicleDto> getVehiclesByUsername(String username) {
        dataOwnershipValidator.belongsToAuthenticatedUser(username);
        LOGGER.info("Get vehicles for given username endpoint used with username:{}", username);
        List<Vehicle> vehicles = vehicleService.getVehiclesByUsername(username);
        return mapper.toVehicleDtoList(vehicles);
    }

    public VehicleDto createVehicle(VehicleCreateRequest vehicleDto, String username) {
        dataOwnershipValidator.belongsToAuthenticatedUser(username);
        LOGGER.info("Create vehicle endpoint used for username:{}", username);
        Vehicle toCreate = mapper.toVehicle(vehicleDto);
        Vehicle createdVehicle =
                vehicleService.createVehicle(toCreate, username, vehicleDto.modelId());
        return mapper.toVehicleDto(createdVehicle);
    }

    public void updateVehicle(VehicleUpdateRequest requestUpdate) {
        LOGGER.info("Update vehicle endpoint used for vehicle id:{}", requestUpdate.vehicleId());
        Vehicle vehicleToUpdate = vehicleService.getVehicle(requestUpdate.vehicleId());
        dataOwnershipValidator.belongsToAuthenticatedUser(vehicleToUpdate);
        Vehicle vehicle = mapper.toVehicle(requestUpdate);
        vehicleService.updateVehicle(vehicleToUpdate, vehicle, requestUpdate.modelId());
    }

    public void deleteVehicle(Long vehicleId) {
        LOGGER.info("Delete vehicle endpoint used for vehicle id:{}", vehicleId);
        Vehicle vehicleToDelete = vehicleService.getVehicle(vehicleId);
        dataOwnershipValidator.belongsToAuthenticatedUser(vehicleToDelete);
        vehicleService.deleteVehicle(vehicleToDelete.getId());
    }

    public List<MakeDto> getMakes() {
        LOGGER.info("Get makes endpoint used.");
        List<Make> makes = vehicleService.getMakes();
        return mapper.toMakeDtoList(makes);
    }

    public List<ModelDto> getModels(String makeName) {
        LOGGER.info("Get models endpoint used for make:{}", makeName);
        List<Model> models = vehicleService.getModelsByMakeId(makeName);
        return mapper.toModelDtoList(models);
    }
}
