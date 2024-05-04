package com.viepovsky.offer;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.garage.model.Garage;
import com.viepovsky.garage.GarageService;
import com.viepovsky.offer.model.CatalogOffer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogOfferService {

    private final CatalogOfferRepository availableCarRepairRepository;

    private final GarageService garageService;

    public List<CatalogOffer> getAllAvailableCarRepair(Long garageId) {
        return availableCarRepairRepository.findAllByGarageId(garageId);
    }

    public CatalogOffer getAvailableCarRepair(Long id) {
        return availableCarRepairRepository.findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("AvailableCarService", id));
    }

    public void saveAvailableCarRepair(CatalogOffer availableCarRepair, Long garageId) {
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
