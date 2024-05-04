package com.viepovsky.garage.garage_work_time;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GarageWorkTimeMapper {

    public GarageWorkTimeDto mapToGarageWorkTimeDto(GarageSchedule garageWorkTime) {
        return new GarageWorkTimeDto(
                garageWorkTime.getId(),
                garageWorkTime.getDay(),
                garageWorkTime.getStartHour(),
                garageWorkTime.getEndHour()
        );
    }

    public List<GarageWorkTimeDto> mapToGarageWorkTimeDtoList(List<GarageSchedule> garageWorkTimeList) {
        return garageWorkTimeList.stream()
                .map(this::mapToGarageWorkTimeDto)
                .toList();
    }

    public GarageSchedule mapToGarageWorkTime(GarageWorkTimeDto garageWorkTimeDto) {
        return new GarageSchedule(
                garageWorkTimeDto.getId(),
                garageWorkTimeDto.getDay(),
                garageWorkTimeDto.getStartHour(),
                garageWorkTimeDto.getEndHour()
        );
    }
}
