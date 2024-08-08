package com.viepovsky.garage;

import com.viepovsky.garage.model.Garage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface GarageRepository extends JpaRepository<Garage, Long> {
}
