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
        LOGGER.info("Get all garages endpoint used.");
        List<Garage> garageList = garageService.getAllGarages();
        return mapper.mapToGarageDtoList(garageList);
    }

    GarageDto getGarage(Long id) {
        LOGGER.info("Get garage endpoint used with id:{}", id);
        var retrievedGarage = garageService.getGarage(id);
        return mapper.mapToGarageDto(retrievedGarage);
    }

    Garage createGarage(GarageDto garageDto) {
        LOGGER.info("Create garage endpoint used.");
        var garageToSave = mapper.mapToGarage(garageDto);
        return garageService.saveGarage(garageToSave);
    }

    void deleteGarage(Long id) {
        LOGGER.info("Delete garage endpoint used with id:{}", id);
        garageService.deleteGarage(id);
    }
}
