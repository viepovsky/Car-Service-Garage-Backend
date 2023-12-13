package com.viepovsky.garage.garage_work_time;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class GarageWorkTimeFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(GarageWorkTimeFacade.class);
    private final GarageWorkTimeService garageWorkTimeService;
    private final GarageWorkTimeMapper mapper;

    List<GarageWorkTimeDto> getGarageWorkTimes(Long garageId) {
        List<GarageWorkTime> workTimes = garageWorkTimeService.getAllGarageWorkTimes(garageId);
        return mapper.mapToGarageWorkTimeDtoList(workTimes);
    }

    void createGarageWorkTime(GarageWorkTimeDto garageWorkTimeDto, Long garageId) {
        LOGGER.info("Create garage work time endpoint used with garage id:{}", garageId);
        GarageWorkTime garageWorkTime = mapper.mapToGarageWorkTime(garageWorkTimeDto);
        garageWorkTimeService.saveGarageWorkTime(garageWorkTime, garageId);
    }

    void deleteGarageWorkTime(Long id) {
        LOGGER.info("Delete garage work time used for id:{}", id);
        garageWorkTimeService.deleteGarageWorkTime(id);
    }
}
