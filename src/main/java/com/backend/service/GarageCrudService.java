package com.backend.service;

import com.backend.domain.Garage;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.repository.GarageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GarageCrudService {
    private GarageRepository garageRepository;

    public List<Garage> getAllGarages(){
        return garageRepository.findAll();
    }

    public Garage getGarage(Long garageId) throws MyEntityNotFoundException {
        return garageRepository.findById(garageId).orElseThrow(() -> new MyEntityNotFoundException("Garage", garageId));
    }

    public Garage saveGarage(Garage garage) {
        return garageRepository.save(garage);
    }

    public Garage updateGarage(Garage garage) throws MyEntityNotFoundException {
        if (garageRepository.findById(garage.getId()).isPresent()) {
            return garageRepository.save(garage);
        } else {
            throw new MyEntityNotFoundException("Garage", garage.getId());
        }
    }

    public void deleteGarage(Long garageId) throws MyEntityNotFoundException {
        if (garageRepository.findById(garageId).isPresent()) {
            garageRepository.deleteById(garageId);
        } else {
            throw new MyEntityNotFoundException("Garage", garageId);
        }
    }
}
