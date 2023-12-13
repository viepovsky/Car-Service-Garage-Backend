package com.viepovsky.car;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class CarFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarFacade.class);
    private final CarService carService;
    private final CarMapper mapper;

    public List<CarDto> getCarsForGivenUsername(String username) {
        LOGGER.info("Get cars for given username endpoint used with username:{}", username);
        List<Car> carList = carService.getAllCarsForGivenUsername(username);
        return mapper.mapToCarDtoList(carList);
    }

    public void createCar(CarDto carDto, String username) {
        LOGGER.info("Create car endpoint used for username:{}", username);
        Car car = mapper.mapToCar(carDto);
        carService.saveCar(car, username);
    }

    public void updateCar(CarDto carDto) {
        LOGGER.info("Update car endpoint used for car id:{}", carDto.getId());
        carService.updateCar(mapper.mapToCar(carDto));
    }

    public void deleteCar(Long carId) {
        LOGGER.info("Delete car endpoint used for car id:{}", carId);
        carService.deleteCar(carId);
    }
}
