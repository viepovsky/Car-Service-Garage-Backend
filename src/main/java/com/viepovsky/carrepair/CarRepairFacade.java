package com.viepovsky.carrepair;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class CarRepairFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarRepairFacade.class);
    private final CarRepairService carRepairService;
    private final CarRepairMapper carRepairMapper;

    public List<CarRepairDto> getCarServices(String username) throws MyEntityNotFoundException {
        LOGGER.info("GET Endpoint used.");
        List<CarRepair> carRepairList = carRepairService.getCarServices(username);
        return carRepairMapper.mapToCarServiceDtoList(carRepairList);
    }

    public void deleteCarService(Long carServiceId) throws MyEntityNotFoundException {
        LOGGER.info("DELETE Endpoint used.");
        carRepairService.deleteCarService(carServiceId);
    }
}
