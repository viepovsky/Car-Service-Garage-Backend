package com.viepovsky.garage.availablerepair;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface AvailableCarRepairRepository extends JpaRepository<AvailableCarRepair, Long> {
    List<AvailableCarRepair> findAllByGarageId(Long garageId);
}
