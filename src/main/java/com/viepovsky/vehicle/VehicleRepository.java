package com.viepovsky.vehicle;

import com.viepovsky.vehicle.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findCarsByUserId(Long userId);
}
