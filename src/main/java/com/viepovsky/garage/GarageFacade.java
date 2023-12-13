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
    private final GarageService service;
    private final GarageMapper mapper;

    List<GarageDto> getAllGarages() {
        LOGGER.info("GET Endpoint used.");
        List<Garage> garageList = service.getAllGarages();
        return mapper.mapToGarageDtoList(garageList);
    }

    GarageDto getGarage(Long garageId) {
        var retrievedGarage = service.getGarage(garageId);
        return mapper.mapToGarageDto(retrievedGarage);
    }

    Garage createGarage(GarageDto garageDto) {
        LOGGER.info("POST Endpoint used.");
        var garageToSave = mapper.mapToGarage(garageDto);
        return service.saveGarage(garageToSave);
    }

    void deleteGarage(Long garageId) {
        service.deleteGarage(garageId);
    }
}
