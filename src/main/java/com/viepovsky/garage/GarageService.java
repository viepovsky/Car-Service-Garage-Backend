package com.viepovsky.garage;

import com.viepovsky.garage.model.Address;
import com.viepovsky.garage.model.Garage;
import com.viepovsky.garage.model.Schedule;
import com.viepovsky.utility.exceptions.MyEntityNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GarageService {

    private final GarageRepository garageRepository;
    private final ScheduleRepository scheduleRepository;

    public List<Garage> getAllGarages() {
        return garageRepository.findAll();
    }

    public Garage getGarage(Long id) {
        return garageRepository
                .findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("Garage " + id));
    }

    public List<String> getAllGarageCities() {
        return garageRepository.findAll().stream()
                .map(Garage::getAddress)
                .map(Address::getCity)
                .toList();
    }

    public Garage saveGarage(Garage garage) {
        return garageRepository.save(garage);
    }

    public void deleteGarage(Long id) {
        if (garageRepository.existsById(id)) {
            garageRepository.deleteById(id);
        } else {
            throw new MyEntityNotFoundException("Garage", id);
        }
    }

    public Schedule saveSchedule(Schedule schedule, Long garageId) {
        Garage garage = getGarage(garageId);
        garage.getGarageSchedules().add(schedule);
        schedule.setGarage(garage);
        return scheduleRepository.save(schedule);
    }
}
