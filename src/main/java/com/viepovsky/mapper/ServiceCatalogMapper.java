package com.viepovsky.mapper;

import com.viepovsky.offer.dto.CatalogOfferDto;
import com.viepovsky.offer.model.CatalogOffer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceCatalogMapper {

    public CatalogOfferDto mapToAvailableCarServiceDto(CatalogOffer availableCarRepair) {
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

    public CatalogOffer mapToAvailableCarService(CatalogOfferDto availableCarRepairDto) {
        //TODO fix this
        return null;
    }

    public List<CatalogOfferDto> mapToAvailableCarServiceDtoList(List<CatalogOffer> availableCarRepairList) {
        return availableCarRepairList.stream()
                .map(this::mapToAvailableCarServiceDto)
                .toList();
    }
}
