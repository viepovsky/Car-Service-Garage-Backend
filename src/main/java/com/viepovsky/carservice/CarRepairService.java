package com.viepovsky.carservice;

import com.viepovsky.booking.Booking;
import com.viepovsky.booking.BookingRepository;
import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.user.User;
import com.viepovsky.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarRepairService {
    private final CarRepairRepository carRepairRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public List<CarRepair> getCarServices(String username) throws MyEntityNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new MyEntityNotFoundException("Username:" + username));
        return carRepairRepository.findCarServicesByUserId(user.getId());
    }

    public CarRepair getCarService(Long id) throws MyEntityNotFoundException {
        return carRepairRepository.findById(id).orElseThrow(() -> new MyEntityNotFoundException("CarService" + id));
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
        CarRepair carRepair = carRepairRepository.findById(carServiceId).orElseThrow(() -> new MyEntityNotFoundException("CarService", carServiceId));
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
            bookingRepository.save(booking);
        } else {
            carRepairRepository.delete(carRepair);
            bookingRepository.delete(booking);
        }
    }
}
