package com.viepovsky.offer;

import com.viepovsky.mapper.OfferSelectedMapper;
import com.viepovsky.offer.dto.SelectedOfferDto;
import com.viepovsky.offer.model.SelectedOffer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class SelectedOfferFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(SelectedOfferFacade.class);
    private final SelectedOfferService carRepairService;
    private final OfferSelectedMapper mapper;

    public List<SelectedOfferDto> getCarRepairs(String username) {
        LOGGER.info("Get car repairs endpoint used for username:{}", username);
        List<SelectedOffer> carRepairList = carRepairService.getCarRepairs(username);
        return mapper.mapToCarServiceDtoList(carRepairList);
    }

    public void deleteCarRepair(Long id) {
        LOGGER.info("Delete car repair used for car repair id:{}", id);
        carRepairService.deleteCarRepair(id);
    }
}
