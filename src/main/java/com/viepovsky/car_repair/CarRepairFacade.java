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

    public List<CarRepairDto> getCarRepairs(String username) {
        LOGGER.info("Get car repairs endpoint used for username:{}", username);
        List<CarRepair> carRepairList = carRepairService.getCarRepairs(username);
        return mapper.mapToCarServiceDtoList(carRepairList);
    }

    public void deleteCarRepair(Long id) {
        LOGGER.info("Delete car repair used for car repair id:{}", id);
        carRepairService.deleteCarRepair(id);
    }
}
