package com.viepovsky.car;

import com.viepovsky.user.User;
import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository repository;
    private final UserService userService;

    public List<Car> getAllCarsForGivenUsername(String username) throws MyEntityNotFoundException {
        Long userId = userService.getUser(username).getId();
        return repository.findCarsByUserId(userId);
    }

    public Car getCar(Long id) throws MyEntityNotFoundException {
        return repository.findById(id).orElseThrow(() -> new MyEntityNotFoundException("Car: " + id));
    }

    public void saveCar(Car car, String username) throws MyEntityNotFoundException {
        User user = userService.getUser(username);
        car.setUser(user);
        user.getCarList().add(car);
        userService.saveUser(user);
    }

    public void updateCar(Car car) throws MyEntityNotFoundException {
        Car retrievedCar = repository.findById(car.getId()).orElseThrow(() -> new MyEntityNotFoundException("Car", car.getId()));
        car.setUser(retrievedCar.getUser());
        repository.save(car);
    }

    public void deleteCar(Long carId) throws MyEntityNotFoundException {
        if (repository.findById(carId).isPresent()) {
            repository.deleteById(carId);
        } else {
            throw new MyEntityNotFoundException("Car", carId);
        }
    }
}
