package com.viepovsky.garage;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class GarageFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(GarageFacade.class);
    private final GarageService garageService;
    private final GarageMapper mapper;

    List<GarageDto> getAllGarages() {
        LOGGER.info("GET Endpoint used.");
        List<Garage> garageList = garageService.getAllGarages();
        return mapper.mapToGarageDtoList(garageList);
    }

    GarageDto getGarage(Long garageId) {
        var retrievedGarage = garageService.getGarage(garageId);
        return mapper.mapToGarageDto(retrievedGarage);
    }

    Garage createGarage(GarageDto garageDto) {
        LOGGER.info("POST Endpoint used.");
        var garageToSave = mapper.mapToGarage(garageDto);
        return garageService.saveGarage(garageToSave);
    }

    void deleteGarage(Long garageId) {
        garageService.deleteGarage(garageId);
    }
}
