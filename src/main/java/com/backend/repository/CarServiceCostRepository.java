package com.backend.repository;

import com.backend.domain.CarServiceCost;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarServiceCostRepository extends CrudRepository<CarServiceCost, Long> {
    List<CarServiceCost> findAll();
    Optional<CarServiceCost> findById(Long id);
}
