package com.viepovsky.carrepair;

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
    private final CarRepairRepository repository;
    private final UserService userService;
    private final BookingService bookingService;

    @Autowired
    public CarRepairService(@Lazy BookingService bookingService, UserService userService, CarRepairRepository repository) {
        this.repository = repository;
        this.userService = userService;
        this.bookingService = bookingService;
    }

    public List<CarRepair> getCarRepairs(String username) {
        User user = userService.getUser(username);
        return repository.findCarServicesByUserId(user.getId());
    }

    public CarRepair getCarRepair(Long id) {
        return repository.findById(id).orElseThrow(() -> new MyEntityNotFoundException("CarRepair" + id));
    }

    public void deleteCarRepair(Long carRepairId) {
        CarRepair carRepair = repository.findById(carRepairId).orElseThrow(() -> new MyEntityNotFoundException("CarRepair", carRepairId));
        Booking booking = carRepair.getBooking();
        if (booking.getCarRepairList().size() > 1) {
            LocalTime endHour = booking.getEndHour();
            endHour = endHour.minusMinutes(carRepair.getRepairTimeInMinutes());
            booking.setEndHour(endHour);

            BigDecimal totalCost = booking.getTotalCost();
            totalCost = totalCost.subtract(carRepair.getCost());
            booking.setTotalCost(totalCost);

            booking.getCarRepairList().remove(carRepair);
            repository.delete(carRepair);
            bookingService.save(booking);
        } else {
            repository.delete(carRepair);
            bookingService.delete(booking);
        }
    }
}
