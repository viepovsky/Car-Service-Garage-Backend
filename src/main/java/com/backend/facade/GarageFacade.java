package com.backend.facade;

import com.backend.config.AdminConfig;
import com.backend.domain.Garage;
import com.backend.domain.dto.GarageDto;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.mapper.GarageMapper;
import com.backend.service.GarageDbService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GarageFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(GarageFacade.class);
    private final GarageDbService garageDbService;
    private final GarageMapper garageMapper;
    private final AdminConfig adminConfig;

    public List<GarageDto> getAllGarages() {
        LOGGER.info("GET Endpoint used.");
        List<Garage> garageList = garageDbService.getAllGarages();
        return garageMapper.mapToGarageDtoList(garageList);
    }

    public ResponseEntity<String> createGarage(GarageDto garageDto, String apiKey) {
        LOGGER.info("POST Endpoint used.");
        if(apiKey.equals(adminConfig.getAdminApiKey())){
            garageDbService.saveGarage(garageMapper.mapToGarage(garageDto));
            return ResponseEntity.ok().build();
        }  else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized. Wrong api-key.");
        }
    }

    public ResponseEntity<String> deleteGarage(Long garageId, String apiKey) throws MyEntityNotFoundException {
        if(apiKey.equals(adminConfig.getAdminApiKey())){
            garageDbService.deleteGarage(garageId);
            return ResponseEntity.ok().build();
        }  else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized. Wrong api-key.");
        }
    }
}