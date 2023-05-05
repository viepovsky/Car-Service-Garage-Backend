package com.viepovsky.garage.worktime;

import com.viepovsky.garage.Garage;
import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.garage.GarageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GarageWorkTimeService {
    private final GarageWorkTimeRepository garageWorkTimeRepository;
    private final GarageRepository garageRepository;

    public List<GarageWorkTime> getAllGarageWorkTimes() {
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
