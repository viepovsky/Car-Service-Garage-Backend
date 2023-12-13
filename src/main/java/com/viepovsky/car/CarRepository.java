package com.viepovsky.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findCarsByUserId(Long userId);
}
