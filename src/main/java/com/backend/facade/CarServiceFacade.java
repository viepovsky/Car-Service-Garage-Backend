package com.backend.facade;

import com.backend.domain.CarService;
import com.backend.domain.dto.CarServiceDto;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.mapper.CarServiceMapper;
import com.backend.service.CarServiceDbService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CarServiceFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarServiceFacade.class);
    private final CarServiceDbService carServiceDbService;
    private final CarServiceMapper carServiceMapper;

    public List<CarServiceDto> getCarServices(String username) throws MyEntityNotFoundException {
        LOGGER.info("GET Endpoint used.");
        List<CarService> carServiceList = carServiceDbService.getCarServices(username);
        return carServiceMapper.mapToCarServiceDtoList(carServiceList);
    }

    public void addCarService(List<Long> selectedServicesIdList, @Min(1) Long carId) throws MyEntityNotFoundException {
        LOGGER.info("POST Endpoint used.");
        carServiceDbService.saveCarService(selectedServicesIdList, carId);
    }

    public void deleteCarService(Long carServiceId) throws MyEntityNotFoundException {
        LOGGER.info("DELETE Endpoint used.");
        carServiceDbService.deleteCarService(carServiceId);
    }
}
