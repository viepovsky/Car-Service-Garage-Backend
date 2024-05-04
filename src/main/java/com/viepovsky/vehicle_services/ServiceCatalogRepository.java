package com.viepovsky.vehicle_services;

import com.viepovsky.vehicle_services.model.ServiceCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface ServiceCatalogRepository extends JpaRepository<ServiceCatalog, Long> {
    List<ServiceCatalog> findAllByGarageId(Long garageId);
}
