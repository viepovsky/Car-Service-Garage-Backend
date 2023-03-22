package com.backend.service;

import com.backend.domain.Garage;
import com.backend.domain.GarageWorkTime;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.repository.GarageRepository;
import com.backend.repository.GarageWorkTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GarageWorkTimeDbService {
    private final GarageWorkTimeRepository garageWorkTimeRepository;
    private final GarageRepository garageRepository;

    public List<GarageWorkTime> getAllGarageWorkTimes(){
        return garageWorkTimeRepository.findAll();
    }

    public void saveGarageWorkTime(GarageWorkTime garageWorkTime, Long garageId) throws MyEntityNotFoundException {
        Garage garage = garageRepository.findById(garageId).orElseThrow(() -> new MyEntityNotFoundException("Garage", garageId));
        garageWorkTime.setGarage(garage);
        garage.getGarageWorkTimeList().add(garageWorkTime);
        garageRepository.save(garage);
    }

    public void deleteGarageWorkTime(Long garageWorkTimeId) throws MyEntityNotFoundException {
        if (garageWorkTimeRepository.findById(garageWorkTimeId).isPresent()) {
            garageWorkTimeRepository.deleteById(garageWorkTimeId);
        } else {
            throw new MyEntityNotFoundException("GarageWorkTime", garageWorkTimeId);
        }
    }
}
