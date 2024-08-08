package com.viepovsky.offer;

import com.viepovsky.offer.dto.SelectedOfferCreateRequest;
import com.viepovsky.offer.dto.SelectedOfferDto;
import com.viepovsky.offer.model.SelectedOffer;
import com.viepovsky.security.DataOwnershipValidator;
import com.viepovsky.utility.mappers.SelectedOfferMapper;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class SelectedOfferFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(SelectedOfferFacade.class);
    private final SelectedOfferService selectedOfferService;
    private final SelectedOfferMapper mapper;
    private final DataOwnershipValidator dataOwnershipValidator;

    public List<SelectedOfferDto> getAllSelectedOffers(String username) {
        dataOwnershipValidator.belongsToAuthenticatedUser(username);
        LOGGER.info("Get all selected offers endpoint used for username:{}", username);
        List<SelectedOffer> carRepairList = selectedOfferService.getAllSelectedOffers(username);
        return mapper.toSelectedOffer(carRepairList);
    }

    public void deleteSelectedOffer(Long selectedOfferId) {
        LOGGER.info("Delete selected offer used for selected offer id:{}", selectedOfferId);
        SelectedOffer selectedOffer = selectedOfferService.getById(selectedOfferId);
        dataOwnershipValidator.belongsToAuthenticatedUser(selectedOffer);
        selectedOfferService.delete(selectedOfferId);
    }

    public SelectedOfferDto createSelectedOffer(
            SelectedOfferCreateRequest request, String username) {
        // TODO: implement this possibility
        return null;
    }
}
