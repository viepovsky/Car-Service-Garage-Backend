package com.backend.service;

import com.backend.domain.AvailableCarService;
import com.backend.domain.Garage;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.repository.AvailableCarServiceRepository;
import com.backend.repository.GarageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailableCarServiceDbService {
    private final AvailableCarServiceRepository availableCarServiceRepository;
    private final GarageRepository garageRepository;

    public List<AvailableCarService> getAllAvailableCarService(){
        return availableCarServiceRepository.findAll();
    }

    public AvailableCarService getAvailableCarService(Long serviceId) throws MyEntityNotFoundException {
        return availableCarServiceRepository.findById(serviceId).orElseThrow(() -> new MyEntityNotFoundException("AvailableCarService", serviceId));
    }

    public void saveAvailableCarService(AvailableCarService availableCarService, Long garageId) throws MyEntityNotFoundException {
        Garage garage = garageRepository.findById(garageId).orElseThrow(() -> new MyEntityNotFoundException("Garage", garageId));
        availableCarService.setGarage(garage);
        garage.getAvailableCarServiceList().add(availableCarService);
        garageRepository.save(garage);
    }

    public AvailableCarService updateAvailableCarService(AvailableCarService availableCarService) throws MyEntityNotFoundException {
        if (availableCarServiceRepository.findById(availableCarService.getId()).isPresent()) {
            return availableCarServiceRepository.save(availableCarService);
        } else {
            throw new MyEntityNotFoundException("AvailableCarService", availableCarService.getId());
        }
    }

    public void deleteAvailableCarService(Long serviceId) throws MyEntityNotFoundException {
        if (availableCarServiceRepository.findById(serviceId).isPresent()) {
            availableCarServiceRepository.deleteById(serviceId);
        } else {
            throw new MyEntityNotFoundException("AvailableCarService", serviceId);
        }
    }
}
