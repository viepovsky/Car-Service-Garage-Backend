package com.viepovsky.carservice;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class CarServiceFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarServiceFacade.class);
    private final CarServiceDbService carServiceDbService;
    private final CarServiceMapper carServiceMapper;

    public List<CarServiceDto> getCarServices(String username) throws MyEntityNotFoundException {
        LOGGER.info("GET Endpoint used.");
        List<CarService> carServiceList = carServiceDbService.getCarServices(username);
        return carServiceMapper.mapToCarServiceDtoList(carServiceList);
    }

    public void deleteCarService(Long carServiceId) throws MyEntityNotFoundException {
        LOGGER.info("DELETE Endpoint used.");
        carServiceDbService.deleteCarService(carServiceId);
    }
}
