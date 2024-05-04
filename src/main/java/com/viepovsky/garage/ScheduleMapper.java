package com.viepovsky.garage;

import com.viepovsky.garage.dto.ScheduleDto;
import com.viepovsky.garage.model.Schedule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleMapper {

    public ScheduleDto mapToGarageWorkTimeDto(Schedule garageWorkTime) {
        //TODO fix this
        return null;
//        return new GarageWorkTimeDto(
//                garageWorkTime.getId(),
//                garageWorkTime.getDay(),
//                garageWorkTime.getOpenFrom(),
//                garageWorkTime.getOpenTill()
//        );
    }

    public List<ScheduleDto> mapToGarageWorkTimeDtoList(List<Schedule> garageWorkTimeList) {
        return garageWorkTimeList.stream()
                .map(this::mapToGarageWorkTimeDto)
                .toList();
    }

    public Schedule mapToGarageWorkTime(ScheduleDto garageWorkTimeDto) {
        //TODO fix this
        return null;
//        return new GarageSchedule(
//                garageWorkTimeDto.getId(),
//                garageWorkTimeDto.getDay(),
//                garageWorkTimeDto.getStartHour(),
//                garageWorkTimeDto.getEndHour()
//        );
    }
}
