package com.viepovsky.vehicle_services;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.garage.model.Garage;
import com.viepovsky.garage.GarageService;
import com.viepovsky.vehicle_services.model.ServiceCatalog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceCatalogService {

    private final ServiceCatalogRepository availableCarRepairRepository;

    private final GarageService garageService;

    public List<ServiceCatalog> getAllAvailableCarRepair(Long garageId) {
        return availableCarRepairRepository.findAllByGarageId(garageId);
    }

    public ServiceCatalog getAvailableCarRepair(Long id) {
        return availableCarRepairRepository.findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("AvailableCarService", id));
    }

    public void saveAvailableCarRepair(ServiceCatalog availableCarRepair, Long garageId) {
        Garage garage = garageService.getGarage(garageId);
        availableCarRepair.setGarage(garage);
        garage.getAvailableServices().add(availableCarRepair);
        garageService.saveGarage(garage);
    }

    public void deleteAvailableCarRepair(Long id) {
        if (availableCarRepairRepository.existsById(id)) {
            availableCarRepairRepository.deleteById(id);
        } else {
            throw new MyEntityNotFoundException("AvailableCarRepair", id);
        }
    }
}
