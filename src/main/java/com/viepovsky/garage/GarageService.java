package com.viepovsky.garage;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GarageService {
    private final GarageRepository garageRepository;

    public List<Garage> getAllGarages() {
        return garageRepository.findAll();
    }

    public Garage getGarage(Long id) throws MyEntityNotFoundException {
        return garageRepository.findById(id).orElseThrow(() -> new MyEntityNotFoundException("Garage " + id));
    }

    public List<String> getAllGarageCities() {
        return garageRepository.findAll().stream().map(n -> n.getAddress().substring(0, n.getAddress().indexOf(" "))).toList();
    }

    public void saveGarage(Garage garage) {
        garageRepository.save(garage);
    }

    public void deleteGarage(Long garageId) throws MyEntityNotFoundException {
        if (garageRepository.findById(garageId).isPresent()) {
            garageRepository.deleteById(garageId);
        } else {
            throw new MyEntityNotFoundException("Garage", garageId);
        }
    }
}
