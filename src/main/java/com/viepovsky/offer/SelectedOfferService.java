package com.viepovsky.offer;

import com.viepovsky.booking.Visit;
import com.viepovsky.booking.BookingService;
import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.offer.model.SelectedOffer;
import com.viepovsky.user.model.AppUser;
import com.viepovsky.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Service
public class SelectedOfferService {
    private final SelectedOfferRepository carRepairRepository;
    private final UserService userService;
    private final BookingService bookingService;

    @Autowired
    public SelectedOfferService(@Lazy BookingService bookingService,
                                UserService userService,
                                SelectedOfferRepository carRepairRepository) {
        this.carRepairRepository = carRepairRepository;
        this.userService = userService;
        this.bookingService = bookingService;
    }

    public List<SelectedOffer> getCarRepairs(String username) {
        AppUser user = userService.getUser(username);
        //TODO fix it
        return null;
//        return carRepairRepository.findAllOfferSelected(user.getId());
    }

    public SelectedOffer getCarRepair(Long id) {
        return carRepairRepository.findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("CarRepair" + id));
    }

    public void deleteCarRepair(Long carRepairId) {
        SelectedOffer carRepair = carRepairRepository.findById(carRepairId)
                                                     .orElseThrow(() -> new MyEntityNotFoundException("CarRepair", carRepairId));
        Visit booking = carRepair.getVisit();
        if (booking.getCarRepairList().size() > 1) {
            LocalTime endHour = booking.getEndHour();
            endHour = endHour.minusMinutes(carRepair.getProbableRepairTime());
            booking.setEndHour(endHour);

            BigDecimal totalCost = booking.getTotalCost();
            totalCost = totalCost.subtract(carRepair.getPrice());
            booking.setTotalCost(totalCost);

            booking.getCarRepairList().remove(carRepair);
            carRepairRepository.delete(carRepair);
            bookingService.save(booking);
        } else {
            carRepairRepository.delete(carRepair);
            bookingService.delete(booking);
        }
    }
}
