package com.viepovsky.car;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends CrudRepository<Car, Long> {
    List<Car> findAll();

    Optional<Car> findById(Long id);

    List<Car> findCarsByUserId(Long userId);
}
