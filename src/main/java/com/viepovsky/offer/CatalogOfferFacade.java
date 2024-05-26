package com.viepovsky.offer;

import com.viepovsky.offer.dto.CatalogOfferCreateRequest;
import com.viepovsky.offer.dto.CatalogOfferDto;
import com.viepovsky.offer.model.CatalogOffer;
import com.viepovsky.utility.mapper.CatalogOfferMapper;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class CatalogOfferFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(CatalogOfferFacade.class);
    private final CatalogOfferService catalogOfferService;
    private final CatalogOfferMapper mapper;

    public List<CatalogOfferDto> getAllCatalogOffers(Long garageId) {
        LOGGER.info("Get all catalog offers endpoint used with garage id:{}", garageId);
        List<CatalogOffer> catalogOffers = catalogOfferService.getAllCatalogOffers(garageId);
        return mapper.mapToAvailableCarServiceDtoList(catalogOffers);
    }

    public void createCatalogOffer(CatalogOfferCreateRequest catalogOfferCreateRequest, Long garageId) {
        LOGGER.info("Create catalog offer service endpoint used with garage id:{}", garageId);
        CatalogOffer catalogOffer = mapper.toCatalogOffer(catalogOfferCreateRequest);
        catalogOfferService.save(catalogOffer, garageId);
    }

    public void deleteCatalogOffer(Long id) {
        LOGGER.info("Delete catalog offer service endpoint used with catalog offer id:{}", id);
        catalogOfferService.delete(id);
    }
}
