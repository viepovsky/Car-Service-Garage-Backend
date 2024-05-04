package com.viepovsky.mapper;

import com.viepovsky.vehicle_services.dto.OfferCatalogDto;
import com.viepovsky.vehicle_services.model.OfferCatalog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceCatalogMapper {

    public OfferCatalogDto mapToAvailableCarServiceDto(OfferCatalog availableCarRepair) {
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

    public OfferCatalog mapToAvailableCarService(OfferCatalogDto availableCarRepairDto) {
        //TODO fix this
        return null;
    }

    public List<OfferCatalogDto> mapToAvailableCarServiceDtoList(List<OfferCatalog> availableCarRepairList) {
        return availableCarRepairList.stream()
                .map(this::mapToAvailableCarServiceDto)
                .toList();
    }
}
