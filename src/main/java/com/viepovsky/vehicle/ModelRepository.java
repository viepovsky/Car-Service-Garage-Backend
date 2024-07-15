package com.viepovsky.vehicle;

import com.viepovsky.vehicle.model.Model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface ModelRepository extends JpaRepository<Model, Long> {
    List<Model> findAllByMakeName(String makeName);
}
