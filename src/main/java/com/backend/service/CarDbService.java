package com.backend.service;

import com.backend.domain.Car;
import com.backend.domain.User;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.repository.CarRepository;
import com.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarDbService {
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    public List<Car> getAllCars(){
        return carRepository.findAll();
    }

    public Car getCar(Long carId) throws MyEntityNotFoundException {
        return carRepository.findById(carId).orElseThrow(() -> new MyEntityNotFoundException("Car", carId));
    }

    public void saveCar(Car car, Long userId) throws MyEntityNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new MyEntityNotFoundException("User", userId));
        car.setUser(user);
        user.getCarList().add(car);
        userRepository.save(user);
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
