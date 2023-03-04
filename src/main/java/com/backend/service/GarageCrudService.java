package com.backend.service;

import com.backend.domain.Garage;
import com.backend.exceptions.GarageNotFoundException;
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

    public Garage getGarage(Long garageId) throws GarageNotFoundException {
        return garageRepository.findById(garageId).orElseThrow(GarageNotFoundException::new);
    }

    public Garage saveGarage(Garage garage) {
        return garageRepository.save(garage);
    }

    public Garage updateGarage(Garage garage) throws GarageNotFoundException {
        if (garageRepository.findById(garage.getId()).isPresent()) {
            return garageRepository.save(garage);
        } else {
            throw new GarageNotFoundException();
        }
    }

    public void deleteGarage(Long garageId) throws GarageNotFoundException {
        if (garageRepository.findById(garageId).isPresent()) {
            garageRepository.deleteById(garageId);
        } else {
            throw new GarageNotFoundException();
        }
    }
}
