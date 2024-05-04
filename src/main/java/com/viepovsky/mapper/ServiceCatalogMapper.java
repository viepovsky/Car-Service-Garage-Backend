package com.viepovsky.mapper;

import com.viepovsky.vehicle_services.dto.ServiceCatalogDto;
import com.viepovsky.vehicle_services.model.ServiceCatalog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceCatalogMapper {

    public ServiceCatalogDto mapToAvailableCarServiceDto(ServiceCatalog availableCarRepair) {
        //TODO fix this
        return null;
//        return new AvailableCarRepairDto(
//                availableCarRepair.getId(),
//                availableCarRepair.getName(),
//                availableCarRepair.getDescription(),
//                availableCarRepair.getPrice(),
//                availableCarRepair.getProbableRepairTime(),
//                availableCarRepair.getPremiumMakes(),
//                availableCarRepair.getMakeMultiplier(),
//                availableCarRepair.getGarage().getId()
//        );
    }

    public ServiceCatalog mapToAvailableCarService(ServiceCatalogDto availableCarRepairDto) {
        //TODO fix this
        return null;
    }

    public List<ServiceCatalogDto> mapToAvailableCarServiceDtoList(List<ServiceCatalog> availableCarRepairList) {
        return availableCarRepairList.stream()
                .map(this::mapToAvailableCarServiceDto)
                .toList();
    }
}
