package com.backend.facade;

import com.backend.config.AdminConfig;
import com.backend.domain.GarageWorkTime;
import com.backend.domain.dto.GarageWorkTimeDto;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.mapper.GarageWorkTimeMapper;
import com.backend.service.GarageWorkTimeDbService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GarageWorkTimeFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(GarageWorkTimeFacade.class);
    private final GarageWorkTimeDbService garageWorkTimeDbService;
    private final GarageWorkTimeMapper garageWorkTimeMapper;
    private final AdminConfig adminConfig;

    public ResponseEntity<String> createGarageWorkTime(GarageWorkTimeDto garageWorkTimeDto, Long garageId, String apiKey) throws MyEntityNotFoundException {
        LOGGER.info("POST Endpoint used.");
        if(apiKey.equals(adminConfig.getAdminApiKey())){
            GarageWorkTime garageWorkTime = garageWorkTimeMapper.mapToGarageWorkTime(garageWorkTimeDto);
            garageWorkTimeDbService.saveGarageWorkTime(garageWorkTime, garageId);
            return ResponseEntity.ok().build();
        }  else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized. Wrong api-key.");
        }
    }

    public ResponseEntity<String> deleteGarageWorkTime(Long garageWorkTimeId, String apiKey) throws MyEntityNotFoundException {
        LOGGER.info("DELETE Endpoint used.");
        if(apiKey.equals(adminConfig.getAdminApiKey())){
            garageWorkTimeDbService.deleteGarageWorkTime(garageWorkTimeId);
            return ResponseEntity.ok().build();
        }  else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized. Wrong api-key.");
        }
    }
}