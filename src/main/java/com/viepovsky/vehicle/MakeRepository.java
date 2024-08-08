package com.viepovsky.vehicle;

import com.viepovsky.vehicle.model.Make;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface MakeRepository extends JpaRepository<Make, Long> {}
