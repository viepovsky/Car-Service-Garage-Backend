package com.viepovsky.vehicle_services;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class AvailableCarRepairMapper {

    public AvailableCarRepairDto mapToAvailableCarServiceDto(ServiceCatalog availableCarRepair) {
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

    public ServiceCatalog mapToAvailableCarService(AvailableCarRepairDto availableCarRepairDto) {
        return new ServiceCatalog(
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

    public List<AvailableCarRepairDto> mapToAvailableCarServiceDtoList(List<ServiceCatalog> availableCarRepairList) {
        return availableCarRepairList.stream()
                .map(this::mapToAvailableCarServiceDto)
                .toList();
    }
}
