package com.viepovsky.utility.mappers;

import com.viepovsky.offer.dto.CatalogOfferCreateRequest;
import com.viepovsky.offer.dto.CatalogOfferDto;
import com.viepovsky.offer.model.CatalogOffer;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogOfferMapper {

    public CatalogOfferDto toCatalogDto(CatalogOffer catalogOffer) {
        return new CatalogOfferDto(
                catalogOffer.getId(),
                catalogOffer.getName(),
                catalogOffer.getDescription(),
                catalogOffer.getPrice(),
                catalogOffer.getProbableRepairTime(),
                catalogOffer.getGarage().getId());
    }

    public CatalogOffer toCatalogOffer(CatalogOfferCreateRequest catalogOfferCreateRequest) {
        return new CatalogOffer(
                catalogOfferCreateRequest.name(),
                catalogOfferCreateRequest.description(),
                catalogOfferCreateRequest.price(),
                catalogOfferCreateRequest.probableRepairTime());
    }

    public List<CatalogOfferDto> toCatalogOffer(List<CatalogOffer> catalogOffers) {
        return catalogOffers.stream().map(this::toCatalogDto).toList();
    }
}
