package com.backend.service;

import com.backend.domain.Car;
import com.backend.domain.CarService;
import com.backend.domain.AvailableCarService;
import com.backend.domain.Customer;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.repository.AvailableCarServiceRepository;
import com.backend.repository.CarRepository;
import com.backend.repository.CarServiceRepository;
import com.backend.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CarServiceDbService {
    private final static String NOT_ASSIGNED = "Not assigned to booking table.";
    private final CarServiceRepository carServiceRepository;
    private final AvailableCarServiceRepository availableCarServiceRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    public List<CarService> getAllCarService(){
        return carServiceRepository.findAll();
    }

    public List<CarService> getAllCarServiceWithGivenCarIdAndNotAssignedStatus(Long carId){
        return carServiceRepository.findCarServicesByCarIdAndStatus(carId, NOT_ASSIGNED);
    }

    public CarService getCarService(Long carServiceId) throws MyEntityNotFoundException {
        return carServiceRepository.findById(carServiceId).orElseThrow(() -> new MyEntityNotFoundException("CarService", carServiceId));
    }

    public CarService saveCarService(CarService carService) {
        return carServiceRepository.save(carService);
    }

    public void saveCarService(List<Long> selectedServices, Long carId) throws MyEntityNotFoundException {
        Car car  = carRepository.findById(carId).orElseThrow(() -> new MyEntityNotFoundException("Car", carId));
        Customer customer = customerRepository.findById(car.getCustomer().getId()).orElseThrow(() -> new MyEntityNotFoundException("Customer", car.getCustomer().getId()));
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
                        customer,
                        NOT_ASSIGNED
            ))
            .toList();
        customer.getCarList().stream()
                .filter(car1 -> Objects.equals(car1.getId(), carId))
                .findFirst()
                .ifPresent(car1 -> car1.getCarServicesList().addAll(carServiceList));
        customer.getServicesList().addAll(carServiceList);
        customerRepository.save(customer);
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
