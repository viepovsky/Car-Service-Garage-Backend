package com.viepovsky.car;

import com.viepovsky.user.User;
import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    public List<Car> getAllCarsForGivenUsername(String username) throws MyEntityNotFoundException {
        Long userId = userRepository.findByUsername(username).orElseThrow(() -> new MyEntityNotFoundException("Username: " + username)).getId();
        return carRepository.findCarsByUserId(userId);
    }

    public Car getCar(Long id) throws MyEntityNotFoundException {
        return carRepository.findById(id).orElseThrow(() -> new MyEntityNotFoundException("Car: " + id));
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
