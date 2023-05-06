package com.viepovsky.garage.worktime;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GarageWorkTimeMapper {

    public GarageWorkTimeDto mapToGarageWorkTimeDto(GarageWorkTime garageWorkTime) {
        return new GarageWorkTimeDto(
                garageWorkTime.getId(),
                garageWorkTime.getDay(),
                garageWorkTime.getStartHour(),
                garageWorkTime.getEndHour()
        );
    }

    public List<GarageWorkTimeDto> mapToGarageWorkTimeDtoList(List<GarageWorkTime> garageWorkTimeList) {
        return garageWorkTimeList.stream()
                .map(this::mapToGarageWorkTimeDto)
                .toList();
    }

    public GarageWorkTime mapToGarageWorkTime(GarageWorkTimeDto garageWorkTimeDto) {
        return new GarageWorkTime(
                garageWorkTimeDto.getId(),
                garageWorkTimeDto.getDay(),
                garageWorkTimeDto.getStartHour(),
                garageWorkTimeDto.getEndHour()
        );
    }
}
