package com.viepovsky.garage.availablerepair;

import com.viepovsky.config.AdminConfig;
import com.viepovsky.exceptions.MyEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class AvailableCarRepairFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(AvailableCarRepairFacade.class);
    private final AvailableCarRepairService service;
    private final AvailableCarRepairMapper mapper;

    public List<AvailableCarRepairDto> getAvailableCarServices(Long garageId) {
        LOGGER.info("GET Endpoint used.");
        List<AvailableCarRepair> availableCarRepairList = service.getAllAvailableCarService(garageId);
        return mapper.mapToAvailableCarServiceDtoList(availableCarRepairList);
    }

    public void createAvailableCarService(AvailableCarRepairDto availableCarRepairDto, Long garageId) throws MyEntityNotFoundException {
        LOGGER.info("POST Endpoint used.");
        AvailableCarRepair availableCarRepair = mapper.mapToAvailableCarService(availableCarRepairDto);
        service.saveAvailableCarService(availableCarRepair, garageId);
    }

    public void deleteAvailableCarService(Long availableCarServiceId) throws MyEntityNotFoundException {
        LOGGER.info("PUT Endpoint used.");
        service.deleteAvailableCarService(availableCarServiceId);
    }
}
