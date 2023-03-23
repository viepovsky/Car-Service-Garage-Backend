package com.backend.facade;

import com.backend.domain.Car;
import com.backend.domain.dto.CarDto;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.mapper.CarMapper;
import com.backend.service.CarDbService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CarFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarFacade.class);
    private final CarDbService carDbService;
    private final CarMapper carMapper;

    public List<CarDto> getCarsForGivenUsername(String username) throws MyEntityNotFoundException {
        LOGGER.info("GET Endpoint used.");
        List<Car> carList = carDbService.getAllCarsForGivenUsername(username);
        return carMapper.mapToCarDtoList(carList);
    }

    public void createCar(CarDto carDto, String username) throws MyEntityNotFoundException {
        LOGGER.info("POST Endpoint used.");
        Car car = carMapper.mapToCar(carDto);
        carDbService.saveCar(car, username);
    }

    public void updateCar(CarDto carDto) throws MyEntityNotFoundException {
        LOGGER.info("PUT Endpoint used.");
        carDbService.updateCar(carMapper.mapToCar(carDto));
    }

    public void deleteCar(Long carId) throws MyEntityNotFoundException {
        LOGGER.info("DELETE Endpoint used.");
        carDbService.deleteCar(carId);
    }
}
