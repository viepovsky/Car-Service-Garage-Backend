package com.viepovsky.garage;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GarageRepository extends CrudRepository<Garage, Long> {
    List<Garage> findAll();

    Optional<Garage> findById(Long id);
}
