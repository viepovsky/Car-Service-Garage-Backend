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

    private final VehicleRepository vehicleRepository;

    private final UserService userService;

    public List<Vehicle> getVehiclesByUsername(String username) {
//        Long userId = userService.getUser(username).getId();
        //TODO check if this works
        return vehicleRepository.findVehiclesByUser_Username(username);
    }

    public Vehicle getCar(Long id) {
        return vehicleRepository
                .findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("Vehicle: " + id));
    }

    public Vehicle saveVehicle(Vehicle vehicle, String username) {
        AppUser user = userService.getUser(username);
        vehicle.setUser(user);
        user.getVehicles().add(vehicle);
        return vehicleRepository.save(vehicle);
    }

    public void updateVehicle(Vehicle vehicle) {
        Vehicle retrievedVehicle =
                vehicleRepository
                        .findById(vehicle.getId())
                        .orElseThrow(() -> new MyEntityNotFoundException("Vehicle", vehicle.getId()));
        vehicle.setUser(retrievedVehicle.getUser());
        vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(Long id) {
        if (vehicleRepository.existsById(id)) {
            vehicleRepository.deleteById(id);
        } else {
            throw new MyEntityNotFoundException("Vehicle", id);
        }
    }
}
