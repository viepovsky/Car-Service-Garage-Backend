package com.backend.service;

import com.backend.domain.Car;
import com.backend.domain.Customer;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.repository.CarRepository;
import com.backend.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarDbService {
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;

    public List<Car> getAllCars(){
        return carRepository.findAll();
    }

    public Car getCar(Long carId) throws MyEntityNotFoundException {
        return carRepository.findById(carId).orElseThrow(() -> new MyEntityNotFoundException("Car", carId));
    }

    public void saveCar(Car car, Long customerId) throws MyEntityNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new MyEntityNotFoundException("Customer", customerId));
        car.setCustomer(customer);
        customer.getCarList().add(car);
        customerRepository.save(customer);
    }

    public Car updateCar(Car car) throws MyEntityNotFoundException {
        if (carRepository.findById(car.getId()).isPresent()) {
            return carRepository.save(car);
        } else {
            throw new MyEntityNotFoundException("Car", car.getId());
        }
    }

    public void deleteCar(Long carId) throws MyEntityNotFoundException {
        if (carRepository.findById(carId).isPresent()) {
            carRepository.deleteById(carId);
        } else {
            throw new MyEntityNotFoundException("Car", carId);
        }
    }
}
