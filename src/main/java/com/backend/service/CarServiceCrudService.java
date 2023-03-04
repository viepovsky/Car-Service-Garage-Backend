package com.backend.service;

import com.backend.domain.CarService;
import com.backend.exceptions.CarNotFoundException;
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

    public CarService getCarService(Long id) throws CarNotFoundException {
        return carServiceRepository.findById(id).orElseThrow(CarNotFoundException::new);
    }

    public CarService saveCarService(CarService carService) {
        return carServiceRepository.save(carService);
    }

    public CarService updateCarService(CarService carService) throws CarNotFoundException {
        if (carServiceRepository.findById(carService.getId()).isPresent()) {
            return carServiceRepository.save(carService);
        } else {
            throw new CarNotFoundException();
        }
    }

    public void deleteCarService(Long id) throws CarNotFoundException {
        if (carServiceRepository.findById(id).isPresent()) {
            carServiceRepository.deleteById(id);
        } else {
            throw new CarNotFoundException();
        }
    }
}
