package com.viepovsky.garage;

import com.viepovsky.garage.dto.CreateScheduleRequest;
import com.viepovsky.garage.dto.GarageCreateRequest;
import com.viepovsky.garage.dto.GarageDto;
import com.viepovsky.garage.dto.ScheduleDto;
import com.viepovsky.garage.model.Garage;
import com.viepovsky.garage.model.Schedule;
import com.viepovsky.utility.mapper.GarageMapper;
import com.viepovsky.utility.mapper.ScheduleMapper;

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
    private final ScheduleMapper schedulemapper;

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

    Garage createGarage(GarageCreateRequest request) {
        LOGGER.info("Create garage endpoint used.");
        var garageToSave = mapper.toGarage(request);
        return garageService.saveGarage(garageToSave);
    }

    void deleteGarage(Long id) {
        LOGGER.info("Delete garage endpoint used with id:{}", id);
        garageService.deleteGarage(id);
    }

    Schedule createSchedule(CreateScheduleRequest request, Long garageId) {
        LOGGER.info("Create schedule endpoint used for garage id:{}", garageId);
        var schedule = mapper.toSchedule(request);
        return garageService.saveSchedule(schedule, garageId);
    }

    List<ScheduleDto> getGarageSchedules(Long garageId) {
        List<Schedule> workTimes = garageService.getAllGarageWorkTimes(garageId);
        return schedulemapper.mapToGarageWorkTimeDtoList(workTimes);
    }

    void createGarageWorkTime(ScheduleDto garageWorkTimeDto, Long garageId) {
        LOGGER.info("Create garage work time endpoint used with garage id:{}", garageId);
        Schedule garageWorkTime = schedulemapper.mapToGarageWorkTime(garageWorkTimeDto);
        garageService.saveGarageWorkTime(garageWorkTime, garageId);
    }

    void deleteSchedule(Long id) {
        LOGGER.info("Delete garage work time used for id:{}", id);
        garageService.deleteGarageWorkTime(id);
    }
}
