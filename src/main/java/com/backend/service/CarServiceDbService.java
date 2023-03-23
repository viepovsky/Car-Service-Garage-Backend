package com.backend.service;

import com.backend.domain.*;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CarServiceDbService {
    private final CarServiceRepository carServiceRepository;
    private final AvailableCarServiceRepository availableCarServiceRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public List<CarService> getCarServices(String username) throws MyEntityNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new MyEntityNotFoundException("Username:" + username));
        return carServiceRepository.findCarServicesByUserId(user.getId());
    }

    public void saveCarService(List<Long> selectedServices, Long carId) throws MyEntityNotFoundException {
        Car car  = carRepository.findById(carId).orElseThrow(() -> new MyEntityNotFoundException("Car", carId));
        User user = userRepository.findById(car.getUser().getId()).orElseThrow(() -> new MyEntityNotFoundException("User", car.getUser().getId()));
        List<AvailableCarService> availableCarServiceList = new ArrayList<>();
        for (Long serviceId : selectedServices) {
            AvailableCarService service = availableCarServiceRepository.findById(serviceId).orElseThrow(() -> new MyEntityNotFoundException("AvailableCarService", serviceId));
            availableCarServiceList.add(service);
        }
        List<CarService> carServiceList = availableCarServiceList.stream()
                .map(selectedService -> new CarService(
                selectedService.getName(),
                selectedService.getDescription(),
                selectedService.getCost(),
                selectedService.getRepairTimeInMinutes(),
                        car,
                        user,
                        ServiceStatus.NOT_ASSIGNED
            ))
            .toList();
        user.getCarList().stream()
                .filter(car1 -> Objects.equals(car1.getId(), carId))
                .findFirst()
                .ifPresent(car1 -> car1.getCarServicesList().addAll(carServiceList));
        user.getServicesList().addAll(carServiceList);
        userRepository.save(user);
    }

    public void deleteCarService(Long carServiceId) throws MyEntityNotFoundException {
        CarService carService  = carServiceRepository.findById(carServiceId).orElseThrow(() -> new MyEntityNotFoundException("CarService", carServiceId));
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
