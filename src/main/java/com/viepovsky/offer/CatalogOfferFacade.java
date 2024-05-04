package com.viepovsky.offer;

import com.viepovsky.offer.dto.CatalogOfferDto;
import com.viepovsky.offer.model.CatalogOffer;
import com.viepovsky.utility.mapper.ServiceCatalogMapper;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class CatalogOfferFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(CatalogOfferFacade.class);

    private final CatalogOfferService availableCarRepairService;

    private final ServiceCatalogMapper mapper;

    public List<CatalogOfferDto> getAvailableCarServices(Long garageId) {
        LOGGER.info("Get available car services enpoint used with garage id:{}", garageId);
        List<CatalogOffer> availableCarRepairList = availableCarRepairService.getAllAvailableCarRepair(garageId);
        return mapper.mapToAvailableCarServiceDtoList(availableCarRepairList);
    }

    public void createAvailableCarService(CatalogOfferDto availableCarRepairDto, Long garageId) {
        LOGGER.info("Create available car service endpoint used with garage id:{}", garageId);
        CatalogOffer availableCarRepair = mapper.mapToAvailableCarService(availableCarRepairDto);
        availableCarRepairService.saveAvailableCarRepair(availableCarRepair, garageId);
    }

    public void deleteAvailableCarService(Long id) {
        LOGGER.info("Delete available car service endpoint used with id:{}", id);
        availableCarRepairService.deleteAvailableCarRepair(id);
    }
}
