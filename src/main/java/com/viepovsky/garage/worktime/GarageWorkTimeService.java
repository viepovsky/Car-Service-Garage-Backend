package com.viepovsky.garage.worktime;

import com.viepovsky.garage.Garage;
import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.garage.GarageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GarageWorkTimeService {
    private final GarageWorkTimeRepository garageWorkTimeRepository;
    private final GarageService garageService;

    public List<GarageWorkTime> getAllGarageWorkTimes() {
        return garageWorkTimeRepository.findAll();
    }

    public void saveGarageWorkTime(GarageWorkTime garageWorkTime, Long garageId) throws MyEntityNotFoundException {
        Garage garage = garageService.getGarage(garageId);
        garageWorkTime.setGarage(garage);
        garage.getGarageWorkTimeList().add(garageWorkTime);
        garageService.saveGarage(garage);
    }

    public void deleteGarageWorkTime(Long garageWorkTimeId) throws MyEntityNotFoundException {
        if (garageWorkTimeRepository.findById(garageWorkTimeId).isPresent()) {
            garageWorkTimeRepository.deleteById(garageWorkTimeId);
        } else {
            throw new MyEntityNotFoundException("GarageWorkTime", garageWorkTimeId);
        }
    }
}
