package com.backend.service;

import com.backend.domain.Car;
import com.backend.domain.CarService;
import com.backend.domain.AvailableCarService;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.repository.CarServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceDbService {
    private final CarServiceRepository carServiceRepository;
    private final AvailableCarServiceDbService availableCarServiceDbService;
    private final CarDbService carDbService;
    public List<CarService> getAllCarService(){
        return carServiceRepository.findAll();
    }

    public CarService getCarService(Long carServiceId) throws MyEntityNotFoundException {
        return carServiceRepository.findById(carServiceId).orElseThrow(() -> new MyEntityNotFoundException("CarService", carServiceId));
    }

    public CarService saveCarService(CarService carService) {
        return carServiceRepository.save(carService);
    }

    public void saveCarService(List<Long> selectedServices, Long carId, Long bookingId) throws MyEntityNotFoundException {
        List<AvailableCarService> availableCarServiceList = new ArrayList<>();
        selectedServices.forEach(serviceId -> {
            try {
                availableCarServiceList.add(availableCarServiceDbService.getAvailableCarService(serviceId));
            } catch (MyEntityNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        List<CarService> carServiceList = availableCarServiceList.stream()
                .map(selectedService -> new CarService(
                selectedService.getId(),
                selectedService.getName(),
                selectedService.getDescription(),
                selectedService.getCost(),
                selectedService.getRepairTimeInMinutes()
            ))
            .toList();
        Car car  = carDbService.getCustomerCar(carId);
        car.setCarServicesList(carServiceList);
        carDbService.updateCustomerCar(car);

        //MUST NOT FORGET TO IMPLEMENT BOOKING !!!
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
}
