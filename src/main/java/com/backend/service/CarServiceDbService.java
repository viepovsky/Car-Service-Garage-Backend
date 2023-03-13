package com.backend.service;

import com.backend.domain.*;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.repository.AvailableCarServiceRepository;
import com.backend.repository.CarRepository;
import com.backend.repository.CarServiceRepository;
import com.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public List<CarService> getAllCarService(){
        return carServiceRepository.findAll();
    }

    public List<CarService> getAllCarServiceWithGivenCarIdAndNotAssignedStatus(Long carId){
        return carServiceRepository.findCarServicesByCarIdAndStatus(carId, ServiceStatus.NOT_ASSIGNED);
    }

    public CarService getCarService(Long carServiceId) throws MyEntityNotFoundException {
        return carServiceRepository.findById(carServiceId).orElseThrow(() -> new MyEntityNotFoundException("CarService", carServiceId));
    }

    public CarService saveCarService(CarService carService) {
        return carServiceRepository.save(carService);
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

    public CarService updateCarService(CarService carService) throws MyEntityNotFoundException {
        if (carServiceRepository.findById(carService.getId()).isPresent()) {
            return carServiceRepository.save(carService);
        } else {
            throw new MyEntityNotFoundException("CarService", carService.getId());
        }
    }

    public void deleteCarService(Long carServiceId) throws MyEntityNotFoundException {
        if (carServiceRepository.findById(carServiceId).isPresent()) {
            carServiceRepository.deleteById(carServiceId);
        } else {
            throw new MyEntityNotFoundException("CarService", carServiceId);
        }
    }

    public void deleteServicesNotAssignedToBooking(Long carId) throws MyEntityNotFoundException {
        Car car = carRepository.findById(carId).orElseThrow(() -> new MyEntityNotFoundException("Car", carId));
        List<Long> serviceIdList = car.getCarServicesList().stream()
                .filter(carService -> carService.getStatus().equals(ServiceStatus.NOT_ASSIGNED))
                .map(CarService::getId)
                .toList();
        serviceIdList.forEach(carRepository::deleteById);
    }
}
