package com.viepovsky.carrepair;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface CarRepairRepository extends CrudRepository<CarRepair, Long> {
    List<CarRepair> findCarServicesByUserId(Long userId);
}
