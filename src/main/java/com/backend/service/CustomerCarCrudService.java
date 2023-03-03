package com.backend.service;

import com.backend.domain.Car;
import com.backend.exceptions.CarNotFoundException;
import com.backend.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerCarCrudService {
    private final CarRepository carRepository;

    public List<Car> getAllCustomerCars(){
        return carRepository.findAll();
    }

    public Car getCustomerCar(Long id) throws CarNotFoundException {
        return carRepository.findById(id).orElseThrow(CarNotFoundException::new);
    }

    public Car saveCustomerCar(Car car) {
        return carRepository.save(car);
    }

    public Car updateCustomerCar(Car car) throws CarNotFoundException {
        if (carRepository.findById(car.getId()).isPresent()) {
            return carRepository.save(car);
        } else {
            throw new CarNotFoundException();
        }
    }

    public void deleteCustomerCar(Long id) throws CarNotFoundException {
        if (carRepository.findById(id).isPresent()) {
            carRepository.deleteById(id);
        } else {
            throw new CarNotFoundException();
        }
    }
}
