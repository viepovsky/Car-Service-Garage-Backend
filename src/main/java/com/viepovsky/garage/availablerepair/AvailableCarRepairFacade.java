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
    private final AvailableCarRepairService availableCarRepairService;
    private final AvailableCarRepairMapper availableCarRepairMapper;
    private final AdminConfig adminConfig;

    public List<AvailableCarRepairDto> getAvailableCarServices(Long garageId) {
        LOGGER.info("GET Endpoint used.");
        List<AvailableCarRepair> availableCarRepairList = availableCarRepairService.getAllAvailableCarService(garageId);
        return availableCarRepairMapper.mapToAvailableCarServiceDtoList(availableCarRepairList);
    }

    public ResponseEntity<String> createAvailableCarService(AvailableCarRepairDto availableCarRepairDto, Long garageId, String apiKey) throws MyEntityNotFoundException {
        LOGGER.info("POST Endpoint used.");
        if (apiKey.equals(adminConfig.getAdminApiKey())) {
            AvailableCarRepair availableCarRepair = availableCarRepairMapper.mapToAvailableCarService(availableCarRepairDto);
            availableCarRepairService.saveAvailableCarService(availableCarRepair, garageId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized. Wrong api-key.");
        }
    }

    public ResponseEntity<String> deleteAvailableCarService(Long availableCarServiceId, String apiKey) throws MyEntityNotFoundException {
        LOGGER.info("PUT Endpoint used.");
        if (apiKey.equals(adminConfig.getAdminApiKey())) {
            availableCarRepairService.deleteAvailableCarService(availableCarServiceId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized. Wrong api-key.");
        }
    }
}
