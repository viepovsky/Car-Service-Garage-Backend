package com.backend.service;

import com.backend.domain.CarService;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.repository.CarServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceCrudService {
    private final CarServiceRepository carServiceRepository;
    public List<CarService> getAllCarService(){
        return carServiceRepository.findAll();
    }

    public CarService getCarService(Long carServiceId) throws MyEntityNotFoundException {
        return carServiceRepository.findById(carServiceId).orElseThrow(() -> new MyEntityNotFoundException("CarService", carServiceId));
    }

    public CarService saveCarService(CarService carService) {
        return carServiceRepository.save(carService);
    }

    public CarService updateCarService(CarService carService) throws MyEntityNotFoundException {
        if (carServiceRepository.findById(carService.getId()).isPresent()) {
            return carServiceRepository.save(carService);
        } else {
            throw new MyEntityNotFoundException("CarService", carService.getId());
        }
    }

    public void deleteCarService(Long carServiceId) throws MyEntityNotFoundException {
        if (carServiceRepository.findById(carServiceId).isPresent()) {
            carServiceRepository.deleteById(carServiceId);
        } else {
            throw new MyEntityNotFoundException("CarService", carServiceId);
        }
    }
}
