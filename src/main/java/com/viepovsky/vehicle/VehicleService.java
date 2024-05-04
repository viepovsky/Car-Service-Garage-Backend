package com.viepovsky.vehicle;

import com.viepovsky.utility.exceptions.MyEntityNotFoundException;
import com.viepovsky.user.model.AppUser;
import com.viepovsky.user.UserService;
import com.viepovsky.vehicle.model.Vehicle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository carRepository;

    private final UserService userService;

    public List<Vehicle> getAllCarsForGivenUsername(String username) {
        Long userId = userService.getUser(username).getId();
        return carRepository.findCarsByUserId(userId);
    }

    public Vehicle getCar(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("Car: " + id));
    }

    public void saveCar(Vehicle car, String username) {
        AppUser user = userService.getUser(username);
        car.setUser(user);
        user.getVehicles().add(car);
        userService.saveUser(user);
    }

    public void updateCar(Vehicle car) {
        Vehicle retrievedCar = carRepository.findById(car.getId())
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
