package com.viepovsky.vehicle_services;

import com.viepovsky.mapper.ServiceCatalogMapper;
import com.viepovsky.vehicle_services.dto.ServiceCatalogDto;
import com.viepovsky.vehicle_services.model.ServiceCatalog;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class ServiceCatalogFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceCatalogFacade.class);

    private final ServiceCatalogService availableCarRepairService;

    private final ServiceCatalogMapper mapper;

    public List<ServiceCatalogDto> getAvailableCarServices(Long garageId) {
        LOGGER.info("Get available car services enpoint used with garage id:{}", garageId);
        List<ServiceCatalog> availableCarRepairList = availableCarRepairService.getAllAvailableCarRepair(garageId);
        return mapper.mapToAvailableCarServiceDtoList(availableCarRepairList);
    }

    public void createAvailableCarService(ServiceCatalogDto availableCarRepairDto, Long garageId) {
        LOGGER.info("Create available car service endpoint used with garage id:{}", garageId);
        ServiceCatalog availableCarRepair = mapper.mapToAvailableCarService(availableCarRepairDto);
        availableCarRepairService.saveAvailableCarRepair(availableCarRepair, garageId);
    }

    public void deleteAvailableCarService(Long id) {
        LOGGER.info("Delete available car service endpoint used with id:{}", id);
        availableCarRepairService.deleteAvailableCarRepair(id);
    }
}
