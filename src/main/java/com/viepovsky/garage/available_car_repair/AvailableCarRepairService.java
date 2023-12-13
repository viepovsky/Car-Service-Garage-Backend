package com.viepovsky.garage.available_car_repair;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.garage.Garage;
import com.viepovsky.garage.GarageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailableCarRepairService {
    private final AvailableCarRepairRepository availableCarRepairRepository;
    private final GarageService garageService;

    public List<AvailableCarRepair> getAllAvailableCarRepair(Long garageId) {
        return availableCarRepairRepository.findAllByGarageId(garageId);
    }

    public AvailableCarRepair getAvailableCarRepair(Long id) {
        return availableCarRepairRepository.findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("AvailableCarService", id));
    }

    public void saveAvailableCarRepair(AvailableCarRepair availableCarRepair, Long garageId) {
        Garage garage = garageService.getGarage(garageId);
        availableCarRepair.setGarage(garage);
        garage.getAvailableCarRepairList().add(availableCarRepair);
        garageService.saveGarage(garage);
    }

    public void deleteAvailableCarRepair(Long id) {
        if (availableCarRepairRepository.existsById(id)) {
            availableCarRepairRepository.deleteById(id);
        } else {
            throw new MyEntityNotFoundException("AvailableCarRepair", id);
        }
    }
}
