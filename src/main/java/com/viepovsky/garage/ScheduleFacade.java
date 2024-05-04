package com.viepovsky.garage;

import com.viepovsky.garage.dto.ScheduleDto;
import com.viepovsky.garage.model.Schedule;
import com.viepovsky.mapper.ScheduleMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class ScheduleFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleFacade.class);

    private final ScheduleService garageWorkTimeService;

    private final ScheduleMapper mapper;

    List<ScheduleDto> getGarageWorkTimes(Long garageId) {
        List<Schedule> workTimes = garageWorkTimeService.getAllGarageWorkTimes(garageId);
        return mapper.mapToGarageWorkTimeDtoList(workTimes);
    }

    void createGarageWorkTime(ScheduleDto garageWorkTimeDto, Long garageId) {
        LOGGER.info("Create garage work time endpoint used with garage id:{}", garageId);
        Schedule garageWorkTime = mapper.mapToGarageWorkTime(garageWorkTimeDto);
        garageWorkTimeService.saveGarageWorkTime(garageWorkTime, garageId);
    }

    void deleteGarageWorkTime(Long id) {
        LOGGER.info("Delete garage work time used for id:{}", id);
        garageWorkTimeService.deleteGarageWorkTime(id);
    }
}
