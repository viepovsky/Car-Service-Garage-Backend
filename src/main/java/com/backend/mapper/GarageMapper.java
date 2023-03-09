package com.backend.mapper;

import com.backend.domain.Garage;
import com.backend.domain.dto.GarageDto;
import org.springframework.stereotype.Service;

@Service
public class GarageMapper {
    public GarageDto mapToGarageDto(Garage garage) {
        return new GarageDto(
                garage.getId(),
                garage.getName(),
                garage.getDescription()
        );
    }

    public Garage mapToGarage(GarageDto garageDto) {
        return new Garage(
                garageDto.getName(),
                garageDto.getDescription()
        );
    }
}
