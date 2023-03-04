package com.backend.repository;

import com.backend.domain.GarageWorkTime;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GarageWorkTimeRepository extends CrudRepository<GarageWorkTime, Long> {
    List<GarageWorkTime> findAll();
    Optional<GarageWorkTime> findById(Long id);
}
