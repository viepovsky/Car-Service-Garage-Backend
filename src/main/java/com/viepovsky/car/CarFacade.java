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
    private final CarMapper carMapper;

    public List<CarDto> getCarsForGivenUsername(String username) {
        LOGGER.info("GET Endpoint used.");
        List<Car> carList = carService.getAllCarsForGivenUsername(username);
        return carMapper.mapToCarDtoList(carList);
    }

    public void createCar(CarDto carDto, String username) {
        LOGGER.info("POST Endpoint used.");
        Car car = carMapper.mapToCar(carDto);
        carService.saveCar(car, username);
    }

    public void updateCar(CarDto carDto) {
        LOGGER.info("PUT Endpoint used.");
        carService.updateCar(carMapper.mapToCar(carDto));
    }

    public void deleteCar(Long carId) {
        LOGGER.info("DELETE Endpoint used.");
        carService.deleteCar(carId);
    }
}
