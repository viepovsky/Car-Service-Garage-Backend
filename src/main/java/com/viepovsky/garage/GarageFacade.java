package com.viepovsky.garage;

import com.viepovsky.garage.dto.ScheduleCreateRequest;
import com.viepovsky.garage.dto.GarageCreateRequest;
import com.viepovsky.garage.dto.GarageDto;
import com.viepovsky.garage.dto.ScheduleDto;
import com.viepovsky.garage.model.Garage;
import com.viepovsky.garage.model.Schedule;
import com.viepovsky.utility.mapper.GarageMapper;

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

    GarageDto getGarage(Long id) {
        LOGGER.info("Get garage endpoint used with id:{}", id);
        var retrievedGarage = garageService.getGarage(id);
        return mapper.toGarageDto(retrievedGarage);
    }

    List<GarageDto> getAllGarages() {
        LOGGER.info("Get all garages endpoint used.");
        List<Garage> garageList = garageService.getAllGarages();
        return mapper.toGarageDtoList(garageList);
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

    List<ScheduleDto> getSchedulesFor(Long garageId) {
        LOGGER.info("Get schedules endpoint used for garage id:{}", garageId);
        List<Schedule> workTimes = garageService.getSchedulesFor(garageId);
        return mapper.toScheduleDto(workTimes);
    }

    ScheduleDto createSchedule(ScheduleCreateRequest request, Long garageId) {
        LOGGER.info("Create schedule endpoint used for garage id:{}", garageId);
        var schedule = mapper.toSchedule(request);
        var createdSchedule = garageService.saveSchedule(schedule, garageId);
        return mapper.toScheduleDto(createdSchedule);
    }

    void deleteSchedule(Long scheduleId) {
        LOGGER.info("Delete schedule used with id:{}", scheduleId);
        garageService.deleteSchedule(scheduleId);
    }
}
