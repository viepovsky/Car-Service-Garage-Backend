package com.viepovsky.garage.worktime;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.garage.Garage;
import com.viepovsky.garage.GarageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GarageWorkTimeService {
    private final GarageWorkTimeRepository repository;
    private final GarageService garageService;

    public List<GarageWorkTime> getAllGarageWorkTimes(Long garageId) {
        return repository.findAllByGarageId(garageId);
    }

    public void saveGarageWorkTime(GarageWorkTime garageWorkTime, Long garageId) {
        Garage garage = garageService.getGarage(garageId);
        garageWorkTime.setGarage(garage);
        garage.getGarageWorkTimeList().add(garageWorkTime);
        garageService.saveGarage(garage);
    }

    public void deleteGarageWorkTime(Long garageWorkTimeId) {
        if (repository.findById(garageWorkTimeId).isPresent()) {
            repository.deleteById(garageWorkTimeId);
        } else {
            throw new MyEntityNotFoundException("GarageWorkTime", garageWorkTimeId);
        }
    }
}
