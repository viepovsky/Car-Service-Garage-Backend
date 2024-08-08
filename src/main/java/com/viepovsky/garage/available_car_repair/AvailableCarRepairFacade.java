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
        LOGGER.info("Get available car services enpoint used with garage id:{}", garageId);
        List<AvailableCarRepair> availableCarRepairList = availableCarRepairService.getAllAvailableCarRepair(garageId);
        return mapper.mapToAvailableCarServiceDtoList(availableCarRepairList);
    }

    public void createAvailableCarService(AvailableCarRepairDto availableCarRepairDto, Long garageId) {
        LOGGER.info("Create available car service endpoint used with garage id:{}", garageId);
        AvailableCarRepair availableCarRepair = mapper.mapToAvailableCarService(availableCarRepairDto);
        availableCarRepairService.saveAvailableCarRepair(availableCarRepair, garageId);
    }

    public void deleteAvailableCarService(Long id) {
        LOGGER.info("Delete available car service endpoint used with id:{}", id);
        availableCarRepairService.deleteAvailableCarRepair(id);
    }
}
