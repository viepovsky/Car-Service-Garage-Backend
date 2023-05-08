package com.viepovsky.carrepair;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface CarRepairRepository extends CrudRepository<CarRepair, Long> {
    List<CarRepair> findAll();

    Optional<CarRepair> findById(Long id);

    List<CarRepair> findCarServicesByCarIdAndStatus(Long carId, RepairStatus status);

    List<CarRepair> findCarServicesByUserId(Long userId);
}
