package com.viepovsky.garage.available_car_repair;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class AvailableCarRepairFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(AvailableCarRepairFacade.class);
    private final AvailableCarRepairService availableCarRepairService;
    private final AvailableCarRepairMapper mapper;

    public List<AvailableCarRepairDto> getAvailableCarServices(Long garageId) {
        LOGGER.info("GET Endpoint used.");
        List<AvailableCarRepair> availableCarRepairList = availableCarRepairService.getAllAvailableCarRepair(garageId);
        return mapper.mapToAvailableCarServiceDtoList(availableCarRepairList);
    }

    public void createAvailableCarService(AvailableCarRepairDto availableCarRepairDto, Long garageId) {
        LOGGER.info("POST Endpoint used.");
        AvailableCarRepair availableCarRepair = mapper.mapToAvailableCarService(availableCarRepairDto);
        availableCarRepairService.saveAvailableCarRepair(availableCarRepair, garageId);
    }

    public void deleteAvailableCarService(Long availableCarServiceId) {
        LOGGER.info("PUT Endpoint used.");
        availableCarRepairService.deleteAvailableCarRepair(availableCarServiceId);
    }
}
