package com.viepovsky.garage;

import com.viepovsky.garage.model.Garage;
import com.viepovsky.garage.model.Schedule;
import com.viepovsky.utility.exceptions.MyEntityNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository garageWorkTimeRepository;

    private final GarageService garageService;

    public List<Schedule> getAllGarageWorkTimes(Long garageId) {
        return garageWorkTimeRepository.findAllByGarageId(garageId);
    }

    public void saveGarageWorkTime(Schedule garageWorkTime, Long garageId) {
        Garage garage = garageService.getGarage(garageId);
        garageWorkTime.setGarage(garage);
        garage.getGarageSchedules().add(garageWorkTime);
        garageService.saveGarage(garage);
    }

    public void deleteGarageWorkTime(Long garageWorkTimeId) {
        if (garageWorkTimeRepository.existsById(garageWorkTimeId)) {
            garageWorkTimeRepository.deleteById(garageWorkTimeId);
        } else {
            throw new MyEntityNotFoundException("GarageWorkTime", garageWorkTimeId);
        }
    }
}
