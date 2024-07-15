package com.viepovsky.vehicle;

import com.viepovsky.user.UserService;
import com.viepovsky.user.model.AppUser;
import com.viepovsky.utility.exceptions.MyEntityNotFoundException;
import com.viepovsky.vehicle.model.Make;
import com.viepovsky.vehicle.model.Model;
import com.viepovsky.vehicle.model.Vehicle;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final ModelRepository modelRepository;
    private final MakeRepository makeRepository;
    private final UserService userService;

    public List<Vehicle> getVehiclesByUsername(String username) {
        return vehicleRepository.findVehiclesByUser_Username(username);
    }

    public Vehicle getVehicle(Long id) {
        return vehicleRepository
                .findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("Vehicle: " + id));
    }

    public Vehicle createVehicle(Vehicle vehicle, String username, Long modelId) {
        AppUser user = userService.getUser(username);
        Model model =
                modelRepository
                        .findById(modelId)
                        .orElseThrow(() -> new MyEntityNotFoundException("Model: " + modelId));
        vehicle.setUser(user);
        vehicle.setModel(model);
        return vehicleRepository.save(vehicle);
    }

    public void updateVehicle(Vehicle vehicleToUpdate, Vehicle vehicle, Long modelId) {
        Model model =
                modelRepository
                        .findById(modelId)
                        .orElseThrow(() -> new MyEntityNotFoundException("Model: " + modelId));
        vehicleToUpdate.updateFrom(vehicle);
        vehicleToUpdate.setModel(model);
        vehicleRepository.save(vehicleToUpdate);
    }

    public void deleteVehicle(Long id) {
        if (vehicleRepository.existsById(id)) {
            vehicleRepository.deleteById(id);
        } else {
            throw new MyEntityNotFoundException("Vehicle", id);
        }
    }

    public List<Make> getMakes() {
        return makeRepository.findAll();
    }

    public List<Model> getModelsByMakeId(String makeName) {
        return modelRepository.findAllByMakeName(makeName);
    }
}
