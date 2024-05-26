package com.viepovsky.utility.mapper;

import com.viepovsky.offer.dto.SelectedOfferDto;
import com.viepovsky.offer.model.SelectedOffer;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SelectedOfferMapper {

    public List<SelectedOfferDto> toSelectedOffer(List<SelectedOffer> selectedOffers) {
        return selectedOffers.stream().map(this::toSelectedOffer).toList();
    }

    public SelectedOfferDto toSelectedOffer(SelectedOffer selectedOffer) {
        return new SelectedOfferDto(
                selectedOffer.getId(),
                selectedOffer.getPrice(),
                selectedOffer.getDiscount(),
                selectedOffer.getProbableRepairTime(),
                selectedOffer.getStatus().name(),
                selectedOffer.getDetails(),
                selectedOffer.getCatalogOffer().getId(),
                selectedOffer.getVisit().getId());
    }
}
