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

    public List<Car> getAllCarsForGivenUsername(String username) throws MyEntityNotFoundException {
        Long userId = userRepository.findByUsername(username).orElseThrow(() -> new MyEntityNotFoundException("Username: " + username)).getId();

        return carRepository.findCarsByUserId(userId);
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

    public void saveCar(Car car, String username) throws MyEntityNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new MyEntityNotFoundException("Username: " + username));
        car.setUser(user);
        user.getCarList().add(car);
        userRepository.save(user);
    }

    public void updateCar(Car car) throws MyEntityNotFoundException {
        Car retrievedCar = carRepository.findById(car.getId()).orElseThrow(() -> new MyEntityNotFoundException("Car", car.getId()));
        car.setUser(retrievedCar.getUser());
        carRepository.save(car);
    }

    public void deleteCar(Long carId) throws MyEntityNotFoundException {
        if (carRepository.findById(carId).isPresent()) {
            carRepository.deleteById(carId);
        } else {
            throw new MyEntityNotFoundException("Car", carId);
        }
    }
}
