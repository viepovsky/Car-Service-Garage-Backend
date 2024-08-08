package com.viepovsky.offer;

import com.viepovsky.garage.GarageService;
import com.viepovsky.garage.model.Garage;
import com.viepovsky.offer.model.CatalogOffer;
import com.viepovsky.utility.exceptions.MyEntityNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogOfferService {
    private final CatalogOfferRepository catalogOfferRepository;
    private final GarageService garageService;

    public List<CatalogOffer> getAllCatalogOffers(Long garageId) {
        return catalogOfferRepository.findAllByGarageId(garageId);
    }

    public CatalogOffer getById(Long id) {
        return catalogOfferRepository
                .findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("CatalogOffer", id));
    }

    public CatalogOffer save(CatalogOffer catalogOffer, Long garageId) {
        Garage garage = garageService.getGarage(garageId);
        garage.getCatalogOffers().add(catalogOffer);
        catalogOffer.setGarage(garage);
        return catalogOfferRepository.save(catalogOffer);
    }

    public void delete(Long id) {
        if (catalogOfferRepository.existsById(id)) {
            catalogOfferRepository.deleteById(id);
        } else {
            throw new MyEntityNotFoundException("CatalogOffer", id);
        }
    }
}
