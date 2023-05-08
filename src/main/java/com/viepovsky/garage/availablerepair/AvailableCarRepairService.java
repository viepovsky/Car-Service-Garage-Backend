package com.viepovsky.garage.availablerepair;

import com.viepovsky.garage.Garage;
import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.garage.GarageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailableCarRepairService {
    private final AvailableCarRepairRepository repository;
    private final GarageService garageService;

    public List<AvailableCarRepair> getAllAvailableCarService(Long garageId) {
        return repository.findAllByGarageId(garageId);
    }

    public AvailableCarRepair getAvailableCarService(Long id) throws MyEntityNotFoundException {
        return repository.findById(id).orElseThrow(() -> new MyEntityNotFoundException("AvailableCarService", id));
    }

    public void saveAvailableCarService(AvailableCarRepair availableCarRepair, Long garageId) throws MyEntityNotFoundException {
        Garage garage = garageService.getGarage(garageId);
        availableCarRepair.setGarage(garage);
        garage.getAvailableCarRepairList().add(availableCarRepair);
        garageService.saveGarage(garage);
    }

    public void deleteAvailableCarService(Long serviceId) throws MyEntityNotFoundException {
        if (repository.findById(serviceId).isPresent()) {
            repository.deleteById(serviceId);
        } else {
            throw new MyEntityNotFoundException("AvailableCarService", serviceId);
        }
    }
}
