package com.viepovsky.garage.availablerepair;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface AvailableCarRepairRepository extends CrudRepository<AvailableCarRepair, Long> {
    List<AvailableCarRepair> findAll();

    List<AvailableCarRepair> findAllByGarageId(Long garageId);

    Optional<AvailableCarRepair> findById(Long id);
}
