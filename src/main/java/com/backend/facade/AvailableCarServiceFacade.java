package com.backend.facade;

import com.backend.config.AdminConfig;
import com.backend.domain.AvailableCarService;
import com.backend.domain.dto.AvailableCarServiceDto;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.mapper.AvailableCarServiceMapper;
import com.backend.service.AvailableCarServiceDbService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AvailableCarServiceFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(AvailableCarServiceFacade.class);
    private final AvailableCarServiceDbService availableCarServiceDbService;
    private final AvailableCarServiceMapper availableCarServiceMapper;
    private final AdminConfig adminConfig;

    public List<AvailableCarServiceDto> getAvailableCarServices(Long garageId) {
        LOGGER.info("GET Endpoint used.");
        List<AvailableCarService> availableCarServiceList = availableCarServiceDbService.getAllAvailableCarService(garageId);
        return availableCarServiceMapper.mapToAvailableCarServiceDtoList(availableCarServiceList);
    }

    public ResponseEntity<String> createAvailableCarService(AvailableCarServiceDto availableCarServiceDto, Long garageId, String apiKey) throws MyEntityNotFoundException {
        LOGGER.info("POST Endpoint used.");
        if(apiKey.equals(adminConfig.getAdminApiKey())){
            AvailableCarService availableCarService = availableCarServiceMapper.mapToAvailableCarService(availableCarServiceDto);
            availableCarServiceDbService.saveAvailableCarService(availableCarService, garageId);
            return ResponseEntity.ok().build();
        }  else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized. Wrong api-key.");
        }
    }

    public ResponseEntity<String> deleteAvailableCarService(Long availableCarServiceId, String apiKey) throws MyEntityNotFoundException {
        LOGGER.info("PUT Endpoint used.");
        if(apiKey.equals(adminConfig.getAdminApiKey())){
            availableCarServiceDbService.deleteAvailableCarService(availableCarServiceId);
            return ResponseEntity.ok().build();
        }  else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized. Wrong api-key.");
        }
    }
}
