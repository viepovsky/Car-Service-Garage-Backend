package com.backend.service;

import com.backend.domain.CarServiceCost;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.repository.CarServiceCostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceCostDbService {
    private final CarServiceCostRepository carServiceCostRepository;

    public List<CarServiceCost> getAllCarServiceCosts(){
        return carServiceCostRepository.findAll();
    }

    public CarServiceCost getCarServiceCost(Long carServiceCostId) throws MyEntityNotFoundException {
        return carServiceCostRepository.findById(carServiceCostId).orElseThrow(() -> new MyEntityNotFoundException("CarServiceCost", carServiceCostId));
    }

    public CarServiceCost saveCarServiceCost(CarServiceCost carServiceCost) {
        return carServiceCostRepository.save(carServiceCost);
    }

    public CarServiceCost updateCarServiceCost(CarServiceCost carServiceCost) throws MyEntityNotFoundException {
        if (carServiceCostRepository.findById(carServiceCost.getId()).isPresent()) {
            return carServiceCostRepository.save(carServiceCost);
        } else {
            throw new MyEntityNotFoundException("CarServiceCost", carServiceCost.getId());
        }
    }

    public void deleteCarServiceCost(Long carServiceCostId) throws MyEntityNotFoundException {
        if (carServiceCostRepository.findById(carServiceCostId).isPresent()) {
            carServiceCostRepository.deleteById(carServiceCostId);
        } else {
            throw new MyEntityNotFoundException("CarServiceCost", carServiceCostId);
        }
    }
}
