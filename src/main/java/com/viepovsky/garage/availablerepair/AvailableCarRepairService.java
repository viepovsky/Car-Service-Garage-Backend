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
    private final AvailableCarRepairRepository availableCarRepairRepository;
    private final GarageService garageService;

    public List<AvailableCarRepair> getAllAvailableCarService(Long garageId) {
        return availableCarRepairRepository.findAllByGarageId(garageId);
    }

    public AvailableCarRepair getAvailableCarService(Long id) throws MyEntityNotFoundException {
        return availableCarRepairRepository.findById(id).orElseThrow(() -> new MyEntityNotFoundException("AvailableCarService", id));
    }

    public void saveAvailableCarService(AvailableCarRepair availableCarRepair, Long garageId) throws MyEntityNotFoundException {
        Garage garage = garageService.getGarage(garageId);
        availableCarRepair.setGarage(garage);
        garage.getAvailableCarRepairList().add(availableCarRepair);
        garageService.saveGarage(garage);
    }

    public void deleteAvailableCarService(Long serviceId) throws MyEntityNotFoundException {
        if (availableCarRepairRepository.findById(serviceId).isPresent()) {
            availableCarRepairRepository.deleteById(serviceId);
        } else {
            throw new MyEntityNotFoundException("AvailableCarService", serviceId);
        }
    }
}
