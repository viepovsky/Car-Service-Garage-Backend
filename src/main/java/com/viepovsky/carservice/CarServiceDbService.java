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
public class CarServiceDbService {
    private final CarServiceRepository carServiceRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public List<CarService> getCarServices(String username) throws MyEntityNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new MyEntityNotFoundException("Username:" + username));
        return carServiceRepository.findCarServicesByUserId(user.getId());
    }

    public CarService getCarService(Long id) throws MyEntityNotFoundException {
        return carServiceRepository.findById(id).orElseThrow(() -> new MyEntityNotFoundException("CarService" + id));
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
        CarService carService = carServiceRepository.findById(carServiceId).orElseThrow(() -> new MyEntityNotFoundException("CarService", carServiceId));
        Booking booking = carService.getBooking();
        if (booking.getCarServiceList().size() > 1) {
            LocalTime endHour = booking.getEndHour();
            endHour = endHour.minusMinutes(carService.getRepairTimeInMinutes());
            booking.setEndHour(endHour);

            BigDecimal totalCost = booking.getTotalCost();
            totalCost = totalCost.subtract(carService.getCost());
            booking.setTotalCost(totalCost);

            booking.getCarServiceList().remove(carService);
            carServiceRepository.delete(carService);
            bookingRepository.save(booking);
        } else {
            carServiceRepository.delete(carService);
            bookingRepository.delete(booking);
        }
    }
}
