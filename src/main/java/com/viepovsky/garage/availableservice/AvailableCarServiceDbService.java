package com.viepovsky.garage.availableservice;

import com.viepovsky.garage.Garage;
import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.garage.GarageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailableCarServiceDbService {
    private final AvailableCarServiceRepository availableCarServiceRepository;
    private final GarageRepository garageRepository;

    public List<AvailableCarService> getAllAvailableCarService(Long garageId) {
        return availableCarServiceRepository.findAllByGarageId(garageId);
    }

    public AvailableCarService getAvailableCarService(Long id) throws MyEntityNotFoundException {
        return availableCarServiceRepository.findById(id).orElseThrow(() -> new MyEntityNotFoundException("AvailableCarService", id));
    }

    public void saveAvailableCarService(AvailableCarService availableCarService, Long garageId) throws MyEntityNotFoundException {
        Garage garage = garageRepository.findById(garageId).orElseThrow(() -> new MyEntityNotFoundException("Garage", garageId));
        availableCarService.setGarage(garage);
        garage.getAvailableCarServiceList().add(availableCarService);
        garageRepository.save(garage);
    }

    public void deleteAvailableCarService(Long serviceId) throws MyEntityNotFoundException {
        if (availableCarServiceRepository.findById(serviceId).isPresent()) {
            availableCarServiceRepository.deleteById(serviceId);
        } else {
            throw new MyEntityNotFoundException("AvailableCarService", serviceId);
        }
    }
}
