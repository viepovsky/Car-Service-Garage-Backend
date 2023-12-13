package com.viepovsky.car_repair;

import com.viepovsky.booking.Booking;
import com.viepovsky.booking.BookingService;
import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.user.User;
import com.viepovsky.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Service
public class CarRepairService {
    private final CarRepairRepository carRepairRepository;
    private final UserService userService;
    private final BookingService bookingService;

    @Autowired
    public CarRepairService(@Lazy BookingService bookingService,
                            UserService userService,
                            CarRepairRepository carRepairRepository) {
        this.carRepairRepository = carRepairRepository;
        this.userService = userService;
        this.bookingService = bookingService;
    }

    public List<CarRepair> getCarRepairs(String username) {
        User user = userService.getUser(username);
        return carRepairRepository.findCarServicesByUserId(user.getId());
    }

    public CarRepair getCarRepair(Long id) {
        return carRepairRepository.findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("CarRepair" + id));
    }

    public void deleteCarRepair(Long carRepairId) {
        CarRepair carRepair = carRepairRepository.findById(carRepairId)
                .orElseThrow(() -> new MyEntityNotFoundException("CarRepair", carRepairId));
        Booking booking = carRepair.getBooking();
        if (booking.getCarRepairList().size() > 1) {
            LocalTime endHour = booking.getEndHour();
            endHour = endHour.minusMinutes(carRepair.getRepairTimeInMinutes());
            booking.setEndHour(endHour);

            BigDecimal totalCost = booking.getTotalCost();
            totalCost = totalCost.subtract(carRepair.getCost());
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
