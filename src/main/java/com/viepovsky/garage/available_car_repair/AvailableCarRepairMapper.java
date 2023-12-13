package com.viepovsky.garage.available_car_repair;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class AvailableCarRepairMapper {

    public AvailableCarRepairDto mapToAvailableCarServiceDto(AvailableCarRepair availableCarRepair) {
        return new AvailableCarRepairDto(
                availableCarRepair.getId(),
                availableCarRepair.getName(),
                availableCarRepair.getDescription(),
                availableCarRepair.getCost(),
                availableCarRepair.getRepairTimeInMinutes(),
                availableCarRepair.getPremiumMakes(),
                availableCarRepair.getMakeMultiplier(),
                availableCarRepair.getGarage().getId()
        );
    }

    public AvailableCarRepair mapToAvailableCarService(AvailableCarRepairDto availableCarRepairDto) {
        return new AvailableCarRepair(
                availableCarRepairDto.getId(),
                availableCarRepairDto.getName(),
                availableCarRepairDto.getDescription(),
                availableCarRepairDto.getCost(),
                availableCarRepairDto.getRepairTimeInMinutes(),
                availableCarRepairDto.getPremiumMakes(),
                availableCarRepairDto.getMakeMultiplier(),
                null
        );
    }

    public List<AvailableCarRepairDto> mapToAvailableCarServiceDtoList(List<AvailableCarRepair> availableCarRepairList) {
        return availableCarRepairList.stream()
                .map(this::mapToAvailableCarServiceDto)
                .toList();
    }
}
