package com.viepovsky.vehicle_services;

import com.viepovsky.vehicle_services.model.OfferCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface OfferCatalogRepository extends JpaRepository<OfferCatalog, Long> {
    List<OfferCatalog> findAllByGarageId(Long garageId);
}
