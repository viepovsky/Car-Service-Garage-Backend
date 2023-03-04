package com.backend.service;

import com.backend.domain.CarServiceCost;
import com.backend.exceptions.CarServiceCostNotFoundException;
import com.backend.repository.CarServiceCostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceCostCrudService {
    private CarServiceCostRepository carServiceCostRepository;

    public List<CarServiceCost> getAllCarServiceCosts(){
        return carServiceCostRepository.findAll();
    }

    public CarServiceCost getCarServiceCost(Long carServiceCostId) throws CarServiceCostNotFoundException {
        return carServiceCostRepository.findById(carServiceCostId).orElseThrow(CarServiceCostNotFoundException::new);
    }

    public CarServiceCost saveCarServiceCost(CarServiceCost carServiceCost) {
        return carServiceCostRepository.save(carServiceCost);
    }

    public CarServiceCost updateCarServiceCost(CarServiceCost carServiceCost) throws CarServiceCostNotFoundException {
        if (carServiceCostRepository.findById(carServiceCost.getId()).isPresent()) {
            return carServiceCostRepository.save(carServiceCost);
        } else {
            throw new CarServiceCostNotFoundException();
        }
    }

    public void deleteCarServiceCost(Long carServiceCostId) throws CarServiceCostNotFoundException {
        if (carServiceCostRepository.findById(carServiceCostId).isPresent()) {
            carServiceCostRepository.deleteById(carServiceCostId);
        } else {
            throw new CarServiceCostNotFoundException();
        }
    }
}
