package com.viepovsky.vehicle_services;

import com.viepovsky.mapper.ServiceCatalogMapper;
import com.viepovsky.vehicle_services.dto.OfferCatalogDto;
import com.viepovsky.vehicle_services.model.OfferCatalog;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class OfferCatalogFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(OfferCatalogFacade.class);

    private final OfferCatalogService availableCarRepairService;

    private final ServiceCatalogMapper mapper;

    public List<OfferCatalogDto> getAvailableCarServices(Long garageId) {
        LOGGER.info("Get available car services enpoint used with garage id:{}", garageId);
        List<OfferCatalog> availableCarRepairList = availableCarRepairService.getAllAvailableCarRepair(garageId);
        return mapper.mapToAvailableCarServiceDtoList(availableCarRepairList);
    }

    public void createAvailableCarService(OfferCatalogDto availableCarRepairDto, Long garageId) {
        LOGGER.info("Create available car service endpoint used with garage id:{}", garageId);
        OfferCatalog availableCarRepair = mapper.mapToAvailableCarService(availableCarRepairDto);
        availableCarRepairService.saveAvailableCarRepair(availableCarRepair, garageId);
    }

    public void deleteAvailableCarService(Long id) {
        LOGGER.info("Delete available car service endpoint used with id:{}", id);
        availableCarRepairService.deleteAvailableCarRepair(id);
    }
}
