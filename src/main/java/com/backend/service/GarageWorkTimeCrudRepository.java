package com.backend.service;

import com.backend.domain.GarageWorkTime;
import com.backend.domain.User;
import com.backend.exceptions.GarageWorkTimeNotFoundException;
import com.backend.exceptions.UserNotFoundException;
import com.backend.repository.GarageWorkTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GarageWorkTimeCrudRepository {
    private GarageWorkTimeRepository garageWorkTimeRepository;

    public List<GarageWorkTime> getAllGarageWorkTimes(){
        return garageWorkTimeRepository.findAll();
    }

    public GarageWorkTime getGarageWorkTime(Long garageWorkTimeId) throws GarageWorkTimeNotFoundException {
        return garageWorkTimeRepository.findById(garageWorkTimeId).orElseThrow(GarageWorkTimeNotFoundException::new);
    }

    public GarageWorkTime saveGarageWorkTime(GarageWorkTime garageWorkTime) {
        return garageWorkTimeRepository.save(garageWorkTime);
    }

    public GarageWorkTime updateGarageWorkTime(GarageWorkTime garageWorkTime) throws GarageWorkTimeNotFoundException {
        if (garageWorkTimeRepository.findById(garageWorkTime.getId()).isPresent()) {
            return garageWorkTimeRepository.save(garageWorkTime);
        } else {
            throw new GarageWorkTimeNotFoundException();
        }
    }

    public void deleteGarageWorkTime(Long garageWorkTimeId) throws GarageWorkTimeNotFoundException {
        if (garageWorkTimeRepository.findById(garageWorkTimeId).isPresent()) {
            garageWorkTimeRepository.deleteById(garageWorkTimeId);
        } else {
            throw new GarageWorkTimeNotFoundException();
        }
    }
}
