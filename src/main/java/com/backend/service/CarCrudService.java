package com.backend.service;

import com.backend.domain.Car;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarCrudService {
    private final CarRepository carRepository;

    public List<Car> getAllCustomerCars(){
        return carRepository.findAll();
    }

    public Car getCustomerCar(Long carId) throws MyEntityNotFoundException {
        return carRepository.findById(carId).orElseThrow(() -> new MyEntityNotFoundException("Car", carId));
    }

    public Car saveCustomerCar(Car car) {
        return carRepository.save(car);
    }

    public Car updateCustomerCar(Car car) throws MyEntityNotFoundException {
        if (carRepository.findById(car.getId()).isPresent()) {
            return carRepository.save(car);
        } else {
            throw new MyEntityNotFoundException("Car", car.getId());
        }
    }

    public void deleteCustomerCar(Long carId) throws MyEntityNotFoundException {
        if (carRepository.findById(carId).isPresent()) {
            carRepository.deleteById(carId);
        } else {
            throw new MyEntityNotFoundException("Car", carId);
        }
    }
}
