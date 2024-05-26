package com.viepovsky.offer;

import com.viepovsky.offer.model.SelectedOffer;
import com.viepovsky.user.UserService;
import com.viepovsky.user.model.AppUser;
import com.viepovsky.utility.exceptions.MyEntityNotFoundException;
import com.viepovsky.visit.VisitService;
import com.viepovsky.visit.model.Visit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Service
public class SelectedOfferService {
    private final SelectedOfferRepository selectedOfferRepository;
    private final UserService userService;
    private final VisitService visitService;

    @Autowired
    public SelectedOfferService(
            @Lazy VisitService visitService,
            UserService userService,
            SelectedOfferRepository selectedOfferRepository) {
        this.selectedOfferRepository = selectedOfferRepository;
        this.userService = userService;
        this.visitService = visitService;
    }

    public List<SelectedOffer> getAllSelectedOffers(String username) {
        AppUser user = userService.getUser(username);
        // TODO fix it
        return null;
    }

    public SelectedOffer getById(Long id) {
        return selectedOfferRepository
                .findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("SelectedOffer" + id));
    }

    public void delete(Long selectedOfferId) {
        SelectedOffer selectedOffer =
                selectedOfferRepository
                        .findById(selectedOfferId)
                        .orElseThrow(
                                () -> new MyEntityNotFoundException("SelectedOffer", selectedOfferId));
        Visit visit = selectedOffer.getVisit();
        if (visit.getSelectedOffers().size() > 1) {
            LocalTime endHour = visit.getVisitEndTime();
            endHour = endHour.minusMinutes(selectedOffer.getProbableRepairTime());
            visit.setVisitEndTime(endHour);

            BigDecimal totalCost = visit.getTotalPrice();
            totalCost = totalCost.subtract(selectedOffer.getPrice());
            visit.setTotalPrice(totalCost);

            visit.getSelectedOffers().remove(selectedOffer);
            selectedOfferRepository.delete(selectedOffer);
            visitService.save(visit);
        } else {
            selectedOfferRepository.delete(selectedOffer);
            visitService.delete(visit);
        }
    }
}
