package com.backend.mapper;

import com.backend.domain.Garage;
import com.backend.domain.dto.GarageDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GarageMapper {
    public GarageDto mapToGarageDto(Garage garage) {
        return new GarageDto(
                garage.getId(),
                garage.getName(),
                garage.getAddress()
        );
    }

    public Garage mapToGarage(GarageDto garageDto) {
        return new Garage(
                garageDto.getName(),
                garageDto.getAddress()
        );
    }

    public List<GarageDto> mapToGarageDtoList(List<Garage> garageList) {
        return garageList.stream()
                .map(this::mapToGarageDto)
                .toList();
    }
}
