package com.viepovsky.car;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.user.User;
import com.viepovsky.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final UserService userService;

    public List<Car> getAllCarsForGivenUsername(String username) {
        Long userId = userService.getUser(username).getId();
        return carRepository.findCarsByUserId(userId);
    }

    public Car getCar(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("Car: " + id));
    }

    public void saveCar(Car car, String username) {
        User user = userService.getUser(username);
        car.setUser(user);
        user.getCarList().add(car);
        userService.saveUser(user);
    }

    public void updateCar(Car car) {
        Car retrievedCar = carRepository.findById(car.getId())
                .orElseThrow(() -> new MyEntityNotFoundException("Car", car.getId()));
        car.setUser(retrievedCar.getUser());
        carRepository.save(car);
    }

    public void deleteCar(Long id) {
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
        } else {
            throw new MyEntityNotFoundException("Car", id);
        }
    }
}
