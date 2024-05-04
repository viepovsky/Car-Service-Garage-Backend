package com.viepovsky.vehicle_services;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.garage.model.Garage;
import com.viepovsky.garage.GarageService;
import com.viepovsky.vehicle_services.model.OfferCatalog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferCatalogService {

    private final OfferCatalogRepository availableCarRepairRepository;

    private final GarageService garageService;

    public List<OfferCatalog> getAllAvailableCarRepair(Long garageId) {
        return availableCarRepairRepository.findAllByGarageId(garageId);
    }

    public OfferCatalog getAvailableCarRepair(Long id) {
        return availableCarRepairRepository.findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("AvailableCarService", id));
    }

    public void saveAvailableCarRepair(OfferCatalog availableCarRepair, Long garageId) {
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
