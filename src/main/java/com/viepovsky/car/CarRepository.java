package com.viepovsky.car;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface CarRepository extends CrudRepository<Car, Long> {
    List<Car> findCarsByUserId(Long userId);
}
