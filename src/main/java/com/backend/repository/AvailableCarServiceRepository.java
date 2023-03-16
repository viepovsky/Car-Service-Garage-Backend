package com.backend.repository;

import com.backend.domain.AvailableCarService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvailableCarServiceRepository extends CrudRepository<AvailableCarService, Long> {
    List<AvailableCarService> findAll();
    List<AvailableCarService> findAllByGarageId(Long garageId);
    Optional<AvailableCarService> findById(Long id);
}
