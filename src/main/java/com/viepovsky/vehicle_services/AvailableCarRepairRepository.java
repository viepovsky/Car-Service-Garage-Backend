package com.viepovsky.vehicle_services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface AvailableCarRepairRepository extends JpaRepository<ServiceCatalog, Long> {
    List<ServiceCatalog> findAllByGarageId(Long garageId);
}
