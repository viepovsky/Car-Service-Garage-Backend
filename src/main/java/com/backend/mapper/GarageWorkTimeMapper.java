package com.backend.mapper;

import com.backend.domain.GarageWorkTime;
import com.backend.domain.dto.GarageWorkTimeDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GarageWorkTimeMapper {

    public GarageWorkTimeDto mapToGarageWorkTimeDto(GarageWorkTime garageWorkTime) {
        return new GarageWorkTimeDto(
                garageWorkTime.getId(),
                garageWorkTime.getDay().toString(),
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
