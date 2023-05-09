package com.viepovsky.garage.worktime;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class GarageWorkTimeFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(GarageWorkTimeFacade.class);
    private final GarageWorkTimeService service;
    private final GarageWorkTimeMapper mapper;

    List<GarageWorkTimeDto> getGarageWorkTimes(Long garageId) {
        List<GarageWorkTime> workTimes = service.getAllGarageWorkTimes(garageId);
        return mapper.mapToGarageWorkTimeDtoList(workTimes);
    }

    void createGarageWorkTime(GarageWorkTimeDto garageWorkTimeDto, Long garageId) throws MyEntityNotFoundException {
        LOGGER.info("POST Endpoint used.");
        GarageWorkTime garageWorkTime = mapper.mapToGarageWorkTime(garageWorkTimeDto);
        service.saveGarageWorkTime(garageWorkTime, garageId);
    }

    void deleteGarageWorkTime(Long garageWorkTimeId) throws MyEntityNotFoundException {
        LOGGER.info("DELETE Endpoint used.");
        service.deleteGarageWorkTime(garageWorkTimeId);
    }
}
