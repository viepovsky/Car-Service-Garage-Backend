package com.viepovsky.car_repair;

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
    private final CarRepairMapper mapper;

    public List<CarRepairDto> getCarServices(String username) {
        LOGGER.info("GET Endpoint used.");
        List<CarRepair> carRepairList = carRepairService.getCarRepairs(username);
        return mapper.mapToCarServiceDtoList(carRepairList);
    }

    public void deleteCarService(Long carServiceId) {
        LOGGER.info("DELETE Endpoint used.");
        carRepairService.deleteCarRepair(carServiceId);
    }
}
