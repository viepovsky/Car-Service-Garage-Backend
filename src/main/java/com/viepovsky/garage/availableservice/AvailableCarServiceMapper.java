package com.viepovsky.garage.availableservice;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class AvailableCarServiceMapper {

    public AvailableCarServiceDto mapToAvailableCarServiceDto(AvailableCarService availableCarService) {
        return new AvailableCarServiceDto(
                availableCarService.getId(),
                availableCarService.getName(),
                availableCarService.getDescription(),
                availableCarService.getCost(),
                availableCarService.getRepairTimeInMinutes(),
                availableCarService.getPremiumMakes(),
                availableCarService.getMakeMultiplier(),
                availableCarService.getGarage().getId()
        );
    }

    public AvailableCarService mapToAvailableCarService(AvailableCarServiceDto availableCarServiceDto) {
        return new AvailableCarService(
                availableCarServiceDto.getId(),
                availableCarServiceDto.getName(),
                availableCarServiceDto.getDescription(),
                availableCarServiceDto.getCost(),
                availableCarServiceDto.getRepairTimeInMinutes(),
                availableCarServiceDto.getPremiumMakes(),
                availableCarServiceDto.getMakeMultiplier(),
                null
        );
    }

    public List<AvailableCarServiceDto> mapToAvailableCarServiceDtoList(List<AvailableCarService> availableCarServiceList) {
        return availableCarServiceList.stream()
                .map(this::mapToAvailableCarServiceDto)
                .toList();
    }
}
