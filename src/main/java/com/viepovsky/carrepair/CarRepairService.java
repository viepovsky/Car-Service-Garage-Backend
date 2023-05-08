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
        this. userService = userService;
        this.bookingService = bookingService;
    }

    public List<CarRepair> getCarServices(String username) throws MyEntityNotFoundException {
        User user = userService.getUser(username);
        return repository.findCarServicesByUserId(user.getId());
    }

    public CarRepair getCarService(Long id) throws MyEntityNotFoundException {
        return repository.findById(id).orElseThrow(() -> new MyEntityNotFoundException("CarService" + id));
    }
//    private final AvailableCarServiceRepository availableCarServiceRepository;
//    private final CarRepository carRepository;
//    public void saveCarService(List<Long> selectedServices, Long carId) throws MyEntityNotFoundException {
//        Car car  = carRepository.findById(carId).orElseThrow(() -> new MyEntityNotFoundException("Car", carId));
//        User user = userRepository.findById(car.getUser().getId()).orElseThrow(() -> new MyEntityNotFoundException("User", car.getUser().getId()));
//        List<AvailableCarService> availableCarServiceList = new ArrayList<>();
//        for (Long serviceId : selectedServices) {
//            AvailableCarService service = availableCarServiceRepository.findById(serviceId).orElseThrow(() -> new MyEntityNotFoundException("AvailableCarService", serviceId));
//            availableCarServiceList.add(service);
//        }
//        List<CarService> carServiceList = availableCarServiceList.stream()
//                .map(selectedService -> new CarService(
//                selectedService.getName(),
//                selectedService.getDescription(),
//                selectedService.getCost(),
//                selectedService.getRepairTimeInMinutes(),
//                        car,
//                        user,
//                        ServiceStatus.NOT_ASSIGNED
//            ))
//            .toList();
//        user.getCarList().stream()
//                .filter(car1 -> Objects.equals(car1.getId(), carId))
//                .findFirst()
//                .ifPresent(car1 -> car1.getCarServicesList().addAll(carServiceList));
//        user.getServicesList().addAll(carServiceList);
//        userRepository.save(user);
//    }

    public void deleteCarService(Long carServiceId) throws MyEntityNotFoundException {
        CarRepair carRepair = repository.findById(carServiceId).orElseThrow(() -> new MyEntityNotFoundException("CarService", carServiceId));
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
