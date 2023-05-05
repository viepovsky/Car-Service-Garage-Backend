package com.viepovsky.carservice;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarServiceRepository extends CrudRepository<CarService, Long> {
    List<CarService> findAll();

    Optional<CarService> findById(Long id);

    List<CarService> findCarServicesByCarIdAndStatus(Long carId, ServiceStatus status);

    List<CarService> findCarServicesByUserId(Long userId);
}
