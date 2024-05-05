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
    private final VehicleService carService;
    private final VehicleMapper mapper;
    private final UserValidator userValidator;

    public List<VehicleDto> getCarsForUsername(String username) {
        userValidator.isValidWithAuthToken(username);
        LOGGER.info("Get cars for given username endpoint used with username:{}", username);
        List<Vehicle> carList = carService.getAllCarsForGivenUsername(username);
        return mapper.mapToCarDtoList(carList);
    }

    public void createCar(VehicleDto carDto, String username) {
        LOGGER.info("Create car endpoint used for username:{}", username);
        Vehicle car = mapper.mapToCar(carDto);
        carService.saveCar(car, username);
    }

    public void updateCar(VehicleDto carDto) {
        LOGGER.info("Update car endpoint used for car id:{}", carDto.getId());
        carService.updateCar(mapper.mapToCar(carDto));
    }

    public void deleteCar(Long carId) {
        LOGGER.info("Delete car endpoint used for car id:{}", carId);
        carService.deleteCar(carId);
    }
}
